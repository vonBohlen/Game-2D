package org.Game2D.engine.chunks;

import org.Game2D.engine.objects.GameObject;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class ChunkMan {

    private static final HashMap<UUID, UUID> objectStorage = new HashMap<>(); // assigns each object a chunk
    public static final int chunkSize = 50; // each chunk is a square with a sidelength of chunksize
    private static final FinderHash chunksByCo = new FinderHash(); // enables to find a chunk via its coordinates
    public static final int updateDistance = 24;
    public static final int renderDistance = 24;
    private static int storedUpdateDistance = updateDistance;
    private static int storedRenderDistance = renderDistance;
    private static Chunk storedChunk = null;
    public static List<Chunk> storedUpdateChunks = new ArrayList<>();
    private static List<Chunk> storedRenderChunks = new ArrayList<>();
    private static ConcurrentHashMap<UUID, Chunk> chunks = new ConcurrentHashMap<>();

    /**
     * Check if a Chunk with given global coordinates exists,
     * and if not, create a new one at the given coordinates
     *
     * @param posX Global x-Coordinate
     * @param posY Global y-Coordinate
     * @return Chunk at the given coordinates
     */
    public static Chunk ChunkFromCoordinates(int posX, int posY) {
        Chunk target = chunksByCo.getChunkByCoordinate(posX, posY);
        if (target == null) {
            target = new Chunk(posX / chunkSize, posY / chunkSize);
            addChunk(target);
        }
        return target;
    }

    public static void printNum(GameObject object, String a){
        int counter = 0;
        for(Chunk chunk : ChunkMan.storedUpdateChunks){

            if(chunk.objects.contains(object)) {
                //Chunk x = chunk;
                counter++;
            }
        }
        System.out.println(a + ": " + counter);
    }

    /**
     * Return the Chunk in which the given GameObject is in
     *
     * @param object GameObject to be searched for
     * @return Chunk of the given GameObject
     */
    public static Chunk getChunkFromObject(GameObject object) {
        return chunks.get(objectStorage.get(object.uuid));
    }

    /**
     * Register a new GameObject in the ChunkManager
     *
     * @param object GameObject to be registered
     * @param chunk  Chunk in which the GameObject is to be stored
     */
    public static void registerObject(GameObject object, Chunk chunk) {
        objectStorage.put(object.uuid, chunk.uuid);
    }

    /**
     * Unregister a GameObject from the ChunkManager
     *
     * @param object GameObject to be unregistered
     */
    public static void unregisterObject(GameObject object) {
        objectStorage.remove(object.uuid);
    }

    /**
     * Add a Chunk object with the ChunkManager
     *
     * @param chunk Chunk to be added
     */
    public static void addChunk(Chunk chunk) {
        chunks.put(chunk.uuid, chunk);
        //System.out.println("New Chunk gets added: x: " + chunk.posX + " y: " + chunk.posY + " Größe jetzt" + chunks.size());
        chunksByCo.addChunk(chunk);
    }

    /**
     * Add a Collection of Chunks to the ChunkManager
     *
     * @param new_chunks Collection of Chunks to be added
     */
    public static void addChunks(Collection<Chunk> new_chunks) {
        for (Chunk i : new_chunks) {
            chunks.put(i.uuid, i);
            //System.out.println("New Chunk gets added: x: " + i.posX + " y: " + i.posY + " Größe jetzt" + chunks.size());
            chunksByCo.addChunk(i);
        }
    }

    /**
     * Update all Chunks in range of the updateDistance from the specified Chunk
     *
     * @param chunk Origin Chunk
     */
    public static void updateByChunk(Chunk chunk) {
        List<Chunk> chunksToUpdate;
        if (storedChunk == chunk && storedUpdateDistance == updateDistance) {
            chunksToUpdate = storedUpdateChunks;
        } else {
            chunksToUpdate = chunksByCo.getChunksInReach(chunk, updateDistance);
            storedUpdateChunks = chunksToUpdate;
            storedChunk = chunk;
            storedUpdateDistance = updateDistance;
        }
        //System.out.println("chunksToUpdate: " + chunksToUpdate.size());
        for (Chunk currentChunk : chunksToUpdate) currentChunk.update();
        //System.out.println("Chunks got updatet: Größe von ChunksToUpdate" + chunksToUpdate.size());
    }

    public static void setRenderDataByChunk(Chunk chunk) {
        List<Chunk> chunksToRender;
        if (storedChunk == chunk && storedRenderDistance == renderDistance) {
            chunksToRender = storedRenderChunks;
        } else {
            chunksToRender = chunksByCo.getChunksInReach(chunk, renderDistance);
            storedRenderChunks = chunksToRender;
            storedChunk = chunk;
            storedRenderDistance = renderDistance;
        }
        for (Chunk currentChunk : chunksToRender) currentChunk.setRenderData();
    }

    /**
     * Flush all Chunk data in the ChunkManager
     */
    public void flushChunks() {
        chunks = new ConcurrentHashMap<>();
    }

}
