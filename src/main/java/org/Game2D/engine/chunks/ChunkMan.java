package org.Game2D.engine.chunks;

import java.util.ArrayList;
import java.util.List;

public class ChunkMan {

    private static final List<Chunk> chunks = new ArrayList<>();

    public static List<Chunk> getChunks() {
        return chunks;
    }

    public static Chunk getChunkById(int id) {
        for (Chunk chunk : chunks) {
            if (chunk.ID == id) return chunk;
        }
        return null;
    }

    public static void addChunk(Chunk chunk) {
        if (!chunks.contains(chunk)) chunks.add(chunk);
    }

    public static void removeChunk(Chunk chunk) {
        chunks.removeIf(current -> current == chunk);
    }

    public static void removeChunkById(int id) {
        chunks.removeIf(chunk -> chunk.ID == id);
    }

}
