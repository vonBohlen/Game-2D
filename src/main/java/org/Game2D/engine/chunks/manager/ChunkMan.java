/**
 * @author The Game2D contributors
 */

package org.Game2D.engine.chunks.manager;

import lombok.NonNull;
import org.Game2D.engine.chunks.Chunk;
import org.Game2D.engine.chunks.utils.data.Directions;
import org.Game2D.engine.chunks.utils.math.FinderHash;
import org.Game2D.engine.data.runtime.DataHand;
import org.Game2D.engine.data.runtime.Instance;
import org.Game2D.engine.objects.GameObject;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

//TODO: Only load the smallest possible amount of chunks from disk into memory to safe recourses
//TODO: Maybe provide tools to game developers, to further optimise this by letting them control this feature to tie loading from disk to player progress in game

public class ChunkMan {

    // Summary of Collections and Objects:
    // 1. Each created chunk gets added to the ConcurrentHashMap chunks where it gets associated with its UUID to enable quick finding of the chunk associated with an object
    // 2. Each created chunk gets added to the FinderHash which allows for fast access based on the chunks coordinates
    // 3. Each GameObject that is created looks for the chunk that it would be in based on its position and creates a chunk if not existent
    // 4. The new GameObject gets registered in its Chunk in objects where it is associated with its UUID to enable quick removal of the object from the chunk
    // 5. The GameObject also gets registered in ChunkMan in objectStorage where its UUID gets associated with its chunks UUID to be able to get the chunk just by looking at the object

    public static int chunkSize; // each chunk is a square with a sidelength of chunk size
    public static int updateDistance;
    public static int renderDistance;
    private static FinderHash chunksByCo; // enables to find a chunk via its coordinates
    private static final HashMap<GameObject,Chunk> objectStorage = new HashMap<>(); // assigns each object a chunks UUID
    private static int storedUpdateDistance;
    private static int storedRenderDistance;

    private static Chunk storedChunk = null; // the pivot element that dictates which chunks get updated and rendered
    private static Chunk lastStoredChunk = null; // the last iterations pivot element that is used to look up whether the chunks to update need to be calculated again
    private static List<Chunk> storedUpdateChunks = new ArrayList<>(); // chunks that got updated in the last cycle
    private static List<Chunk> storedRenderChunks = new ArrayList<>(); // chunks that got rendered in the last cycle

    /**
     * Initializes the Chunk system.
     */
    public static void initialize() {

        Instance instance = DataHand.instance;

        chunkSize = instance.chunkSize;
        updateDistance = instance.updateDistance;
        renderDistance = instance.renderDistance;

        storedUpdateDistance = updateDistance;
        storedRenderDistance = renderDistance;

        chunksByCo = new FinderHash();

        //Create a single Chunk, otherwise no other Chunks can be added
        ChunkMan.addChunk(new Chunk(0, 0));

    }

    /**
     * Check if a Chunk with given global coordinates exists,
     * and if not, create a new one at the given coordinates
     *
     * @param posX Global x-Coordinate
     * @param posY Global y-Coordinate
     * @return Chunk at the given coordinates
     */
    public static @NonNull Chunk ChunkFromCoordinates(int posX, int posY) {
        Chunk target = chunksByCo.getChunkByCoordinate(posX, posY);
        if (target == null) {
            target = new Chunk(posX / chunkSize, posY / chunkSize);
            addChunk(target);
        }
        return target;
    }

    /**
     * Return the Chunk in which the given GameObject is in
     *
     * @param object GameObject to be searched for
     * @return Chunk of the given GameObject
     */
    public static Chunk getChunkFromObject(@NonNull GameObject object) {
        return objectStorage.get(object);
    }

    public static Chunk getAdjacentChunk(Chunk chunk, @NonNull Directions direction) {
        return chunksByCo.getAdjacentChunk(chunk, direction);
    }

    /**
     * Register a new GameObject in the ChunkManager
     *
     * @param object GameObject to be registered
     * @param chunk  Chunk in which the GameObject is to be stored
     */
    public static void registerObject(@NonNull GameObject object, @NonNull Chunk chunk) {
        objectStorage.put(object, chunk);
    }

    /**
     * Unregister a GameObject from the ChunkManager
     *
     * @param object GameObject to be unregistered
     */
    public static void unregisterObject(@NonNull GameObject object) {
        objectStorage.remove(object);
    }

    public static int getTotalObjectCount() {
        return objectStorage.size();
    }

    /**
     * Add a Chunk object with the ChunkManager
     *
     * @param chunk Chunk to be added
     */
    public static void addChunk(Chunk chunk) {
        chunksByCo.addChunk(chunk);
    }

    /**
     * Add a Collection of Chunks to the ChunkManager
     *
     * @param newChunks Collection of Chunks to be added
     */
    public static void addChunks(@NonNull Collection<Chunk> newChunks) {
        for (Chunk i : newChunks) {
            chunksByCo.addChunk(i);
        }
    }

    /**
     * Update all Chunks (and in turn the GameObjects contained in them)
     * in range of the updateDistance from the specified Chunk
     */
    public static void updateByChunk() {
        // renderMan hasn't initialized yet
        if (storedChunk == null) {
            return;
        }

        // selecting chunks to update
        List<Chunk> chunksToUpdate;
        if (storedChunk == lastStoredChunk && storedUpdateDistance == updateDistance) {
            chunksToUpdate = storedUpdateChunks;
        } else {
            chunksToUpdate = chunksByCo.getChunksInReach(storedChunk, updateDistance);
            storedUpdateChunks = chunksToUpdate;
            storedUpdateDistance = updateDistance;
        }
        lastStoredChunk = storedChunk;

        // setting up the TransferManager to be able to shift elements between chunks
        ObjectTransferMan.prepareObjectTransfer();

        // update objects in chunks
        for (Chunk currentChunk : chunksToUpdate) currentChunk.update();

        //conclude the update by transferring objects to their new chunks
        ObjectTransferMan.transferQueue();
    }

    /**
     * Render all Chunks (and in turn the GameObjects contained in them)
     * in range of the updateDistance from the specified Chunk
     *
     * @param g2
     * @param chunk
     * @param renderHitboxes
     * @param renderActiveChunks
     */
    public static void setRenderDataByChunk(Graphics2D g2, Chunk chunk, boolean renderHitboxes, boolean renderActiveChunks) {
        List<Chunk> chunksToRender;
        if (storedChunk == chunk && storedRenderDistance == renderDistance) {
            chunksToRender = storedRenderChunks;
        } else {
            chunksToRender = chunksByCo.getChunksInReach(chunk, renderDistance);
            storedRenderChunks = chunksToRender;
            storedChunk = chunk;
            storedRenderDistance = renderDistance;
        }
        // go through each chunk and setRenderData objects in them
        for (Chunk currentChunk : chunksToRender) currentChunk.setRenderData(g2, renderHitboxes, renderActiveChunks);
    }

    /**
     * Flush all Chunk data in the ChunkManager
     */
    public void flushChunks() {
        storedChunk = null;
        storedUpdateChunks = new ArrayList<>();
        storedRenderChunks = new ArrayList<>();
    }

}
