package org.Game2D.engine.chunks;

import org.Game2D.engine.objects.GameObject;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class ChunkMan {

    public static int chunkSize = 2000;

    public static int updateDistance = 24;
    public static int renderDistance = 24;

    private static ConcurrentHashMap<UUID, Chunk> chunks = new ConcurrentHashMap<>();
    private static HashMap<UUID, UUID> objectStorage = new HashMap<>();
    private static FinderHash chunksByCo = new FinderHash(chunkSize);

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
        if(target == null){
            Chunk new_chunk = new Chunk(posX, posY);
            addChunk(new_chunk);
            target = new_chunk;
        }
        if(target == null){
            System.out.println(target == null);
        }

        return target;
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

    public static void addChunk(Chunk chunk) {
        chunks.put(chunk.uuid, chunk);
        chunksByCo.addChunk(chunk);
    }

    public static void addChunks(Collection<Chunk> new_chunks) {
        for (Chunk i : new_chunks) {
            chunks.put(i.uuid, i);
            chunksByCo.addChunk(i);
        }
    }

    public static void updateByChunk(Chunk chunk) {
        List<Chunk> chunks = chunksByCo.getChunksInReach(chunk, updateDistance);
        for (Chunk currentChunk : chunks) currentChunk.update();
    }

    public static void setRenderDataByChunk(Chunk chunk) {
        List<Chunk> chunks = chunksByCo.getChunksInReach(chunk, renderDistance);
        for (Chunk currentChunk : chunks) currentChunk.setRenderData();
    }

    public void flushChunks() {
        chunks = new ConcurrentHashMap<>();
    }

}
