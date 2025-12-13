package org.Game2D.engine.chunks;

import org.Game2D.engine.objects.GameObject;

import java.util.LinkedList;
import java.util.List;

public class ChunkMan {

    int chunkSize = 2000;

    int updateDistance = 24;
    int renderDistance = 24;

    private static List<Chunk> chunks = new LinkedList<>();

    public Chunk targetChunkByPosition(GameObject object) {
        int posX = object.hitBox.x / chunkSize;
        int posY = object.hitBox.y / chunkSize;
        for (Chunk chunk : chunks) {
            if (chunk.position[0] == posX && chunk.position[1] == posY) return chunk;
        }
        throw new RuntimeException();
    }

    public List<Chunk> calcWantedChunks(Chunk chunk) {

    }

    public void updateByChunk(Chunk chunk) {

    }

    public void renderByChunk(Chunk chunk) {

    }

}
