package org.Game2D.engine.chunks;

import org.Game2D.engine.objects.GameObject;

import java.util.*;

public class ChunkMan {

    public static int chunkSize = 2000;

    public static int updateDistance = 24;
    public static int renderDistance = 24;

    //private static List<Chunk> chunks = new LinkedList<>();
    private static HashMap<UUID, Chunk> chunks = new HashMap<>();
    private static HashMap<UUID, UUID> objectStorage = new HashMap<>();

    public static Chunk getChunkFromObject(GameObject object) {
        return chunks.get(objectStorage.get(object.uuid));
    }

    public static void registerObject(GameObject object, Chunk chunk) {
        objectStorage.put(object.uuid, chunk.uuid);
    }

    public static void unregisterObject(GameObject object, Chunk chunk) {
        objectStorage.remove(object.uuid);
    }

    public static void addChunk(Chunk chunk) {
        chunks.put(chunk.uuid, chunk);
    }

    public static void addChunks(Collection<Chunk> new_chunks) {
        for (Chunk i : new_chunks) {
            chunks.put(i.uuid, i);
        }
    }

    public void flushChunks() {
        chunks = new HashMap<>();
    }

    public static Chunk targetChunkByPosition(GameObject object) {
        int posX = object.hitBox.x / chunkSize;
        int posY = object.hitBox.y / chunkSize;
        for (UUID i : chunks.keySet()) {
            if (chunks.get(i).posX == posX && chunks.get(i).posY == posY) return chunks.get(i);
        }
        throw new RuntimeException();
    }

    public static Chunk targetChunkByCoordinates(int x, int y) {
        int posX = x / chunkSize;
        int posY = y / chunkSize;
        for (UUID i : chunks.keySet()) {
            if (chunks.get(i).posX == posX && chunks.get(i).posY == posY) return chunks.get(i);
        }
        throw new RuntimeException();
    }

    public static List<Chunk> calcWantedChunks(Chunk chunk, int calcDiam) {
        List<Chunk> chunks = new LinkedList<>();
        int scannerMaxX = chunk.posX + calcDiam / 2;
        int scannerMaxY = chunk.posY + calcDiam / 2;
        int scannerMinX = scannerMaxX - calcDiam;
        int scannerMinY = scannerMaxY - calcDiam;
        int scannerCurrentX = scannerMaxX;
        int scannerCurrentY = scannerMaxY;
        while (true) {
            for (UUID currentChunk_uuid : ChunkMan.chunks.keySet()) {
                if (chunk.posX == scannerCurrentX && chunk.posY == scannerCurrentY) {
                    chunks.add(ChunkMan.chunks.get(currentChunk_uuid));
                }
            }
            scannerCurrentX--;
            if (scannerCurrentX < scannerMinX) {
                scannerCurrentX = scannerMaxX;
                scannerCurrentY--;
            }
            if (scannerCurrentY < scannerMinY) {
                return chunks;
            }
        }
    }

    public static void updateByChunk(Chunk chunk) {
        List<Chunk> chunks = calcWantedChunks(chunk, updateDistance);
        for (Chunk currentChunk : chunks) currentChunk.update();
    }

    public static void setRenderDataByChunk(Chunk chunk) {
        List<Chunk> chunks = calcWantedChunks(chunk, renderDistance);
        for (Chunk currentChunk : chunks) currentChunk.setRenderData();
    }

}
