package org.Game2D.engine.chunks;

import org.Game2D.engine.objects.GameObject;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class ChunkMan {

    static int chunkSize = 2000;

    static int updateDistance = 24;
    static int renderDistance = 24;

    private static List<Chunk> chunks = new LinkedList<>();

    public static void addChunk(Chunk chunk) {
        chunks.add(chunk);
    }

    public static void addChunks(Collection<Chunk> chunks) {
        ChunkMan.chunks.addAll(chunks);
    }

    public void flushChunks() {
        chunks = new LinkedList<>();
    }

    public static Chunk targetChunkByPosition(GameObject object) {
        int posX = object.hitBox.x / chunkSize;
        int posY = object.hitBox.y / chunkSize;
        for (Chunk chunk : chunks) {
            if (chunk.position[0] == posX && chunk.position[1] == posY) return chunk;
        }
        throw new RuntimeException();
    }

    public static Chunk targetChunkByCoordinates(int x, int y) {
        int posX = x / chunkSize;
        int posY = y / chunkSize;
        for (Chunk chunk : chunks) {
            if (chunk.position[0] == posX && chunk.position[1] == posY) return chunk;
        }
        throw new RuntimeException();
    }

    public static List<Chunk> calcWantedChunks(Chunk chunk, int calcDiam) {
        List<Chunk> chunks = new LinkedList<>();
        int scannerMaxX = chunk.position[0] + calcDiam / 2;
        int scannerMaxY = chunk.position[1] + calcDiam / 2;
        int scannerMinX = scannerMaxX - calcDiam;
        int scannerMinY = scannerMaxY - calcDiam;
        int scannerCurrentX = scannerMaxX;
        int scannerCurrentY = scannerMaxY;
        while (true) {
            for (Chunk currentChunk : ChunkMan.chunks) {
                if (chunk.position[0] == scannerCurrentX && chunk.position[1] == scannerCurrentY) {
                    chunks.add(currentChunk);
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

    public static void renderByChunk(Chunk chunk) {
        List<Chunk> chunks = calcWantedChunks(chunk, renderDistance);
        for (Chunk currentChunk : chunks) currentChunk.setRenderData();
    }

}
