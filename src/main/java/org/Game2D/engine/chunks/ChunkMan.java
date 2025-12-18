package org.Game2D.engine.chunks;

import org.Game2D.engine.objects.GameObject;
import lombok.NonNull;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

//TODO: Only load the smallest possible amount of chunks from disk into memory to safe recourses
//TODO: Maybe provide tools to game developers, to further optimise this by letting them control this feature to tie loading from disk to player progress in game

public class ChunkMan {

    //Summary of Lists and Objects:
    // 1. Each created chunk gets added to the ConcurrentHashMap chunks where it gets associated with its UUID to enable quick finding of the chunk associated with an object
    // 2. Each created chunk gets added to the FinderHash which allows for fast access based on the chunks coordinates
    // 3. Each GameObject that is created looks for the chunk that it would be in based on its position and creates a chunk if not existent
    // 4. The new GameObject gets registered in its Chunk in objects where it is associated with its UUID to enable quick removal of the object from the chunk
    // 5. The GameObject also gets registered in ChunkMan in objectStorage where its UUID gets associated with its chunks UUID to be able to get the chunk just by looking at the object

    //TODO: Kann man nicht einfach objectStorage als HashMap<GameObject,Chunk> machen?
    //TODO: objectStorage nach FinderHash verschieben um Chunk-finde-sachen alle in einer Klasse zu haben
    //TODO: Ja, Justus sagt das das möglich wäre

    public static final int chunkSize = 512; // each chunk is a square with a sidelength of chunk size
    public static final int updateDistance = 24;
    public static final int renderDistance = 12;
    private static final FinderHash chunksByCo = new FinderHash(); // enables to find a chunk via its coordinates
    private static final HashMap<UUID, UUID> objectStorage = new HashMap<>(); // assigns each object a chunks UUID
    public static ConcurrentHashMap<UUID, Chunk> chunks = new ConcurrentHashMap<>(); // all chunks with their UUID
    private static int storedUpdateDistance = updateDistance;
    private static int storedRenderDistance = renderDistance;

    private static Chunk storedChunk = null; // the pivot element that dictates which chunks get updated and rendered
    private static Chunk lastStoredChunk = null; // the last iterations pivot element that is used to look up whether the chunks to update need to be calculated again
    private static List<Chunk> storedUpdateChunks = new ArrayList<>(); // chunks that got updated in the last cycle
    private static List<Chunk> storedRenderChunks = new ArrayList<>(); // chunks that got rendered in the last cycle

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
        return chunks.get(objectStorage.get(object.uuid));
    }

    /**
     * Register a new GameObject in the ChunkManager
     *
     * @param object GameObject to be registered
     * @param chunk  Chunk in which the GameObject is to be stored
     */
    public static void registerObject(@NonNull GameObject object, @NonNull Chunk chunk) {
        objectStorage.put(object.uuid, chunk.uuid);
    }

    /**
     * Unregister a GameObject from the ChunkManager
     *
     * @param object GameObject to be unregistered
     */
    public static void unregisterObject(@NonNull GameObject object) {
        objectStorage.remove(object.uuid);
    }

    /**
     * Add a Chunk object with the ChunkManager
     *
     * @param chunk Chunk to be added
     */
    public static void addChunk(Chunk chunk) {
        chunks.put(chunk.uuid, chunk);
        chunksByCo.addChunk(chunk);
    }

    /**
     * Add a Collection of Chunks to the ChunkManager
     *
     * @param new_chunks Collection of Chunks to be added
     */
    public static void addChunks(@NonNull Collection<Chunk> new_chunks) {
        for (Chunk i : new_chunks) {
            chunks.put(i.uuid, i);
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
     * @param chunk
     * @param g2
     * @param renderHitboxes
     * @param renderActiveChunks
     */
    public static void renderByChunk(Chunk chunk, Graphics2D g2, boolean renderHitboxes, boolean renderActiveChunks) {
        List<Chunk> chunksToRender;
        if (storedChunk == chunk && storedRenderDistance == renderDistance) {
            chunksToRender = storedRenderChunks;
        } else {
            chunksToRender = chunksByCo.getChunksInReach(chunk, renderDistance);
            storedRenderChunks = chunksToRender;
            storedChunk = chunk;
            storedRenderDistance = renderDistance;
        }
        // go through each chunk and render objects in them
        for (Chunk currentChunk : chunksToRender) currentChunk.render(g2, renderHitboxes, renderActiveChunks);
    }

    /**
     * Flush all Chunk data in the ChunkManager
     */
    public void flushChunks() {
        storedChunk = null;
        storedUpdateChunks = new ArrayList<>();
        storedRenderChunks = new ArrayList<>();
        chunks = new ConcurrentHashMap<>();
    }

}
