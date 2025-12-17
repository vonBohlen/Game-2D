package org.Game2D.engine.chunks;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Dark Magic for quickly identifying Chunks by their coordinates
 * and finding nearby Chunks
 */
public class FinderHash {

    private final int chunkDimensions;
    /**
     * Index-Chunk Hashmap of all registered Chunks
     */
    private ConcurrentHashMap<Integer, Chunk> chunkPos;

    FinderHash() {
        chunkPos = new ConcurrentHashMap<>();
        chunkDimensions = ChunkMan.chunkSize;
    }

    /**
     * Assign a unique index to the specified coordinates
     *
     * @param x x-Coordinate
     * @param y y-Coordinate
     * @return Unique index of the coordinate pair
     */
    private int getIndex(int x, int y) {
        // 1. Bijection from Z to N_0

        // x -> n1
        // 0, 1, 2, ... get projected on 0, 2, 4, ... (even)
        // -1, -2, -3, ... get projected on 1, 3, 5, ... (uneven)
        long n1;
        if (x >= 0) {
            n1 = 2L * x;
        } else {
            n1 = -2L * x - 1;
        }

        // y -> n2:
        long n2;
        if (y >= 0) {
            n2 = 2L * y;
        } else {
            n2 = -2L * y - 1;
        }

        // 2. Bijection from N_0^2 to N_0

        // S = n1 + n2
        long sum = n1 + n2;

        // Index = 0.5 * (S) * (S + 1) + n2
        long index = sum * (sum + 1) / 2 + n2;

        return (int) index;
    }

    /**
     * Store a Chunk with its respective index for faster retrieval in the future
     *
     * @param chunk Chunk to be stored
     */
    public void addChunk(Chunk chunk) {
        chunkPos.put(getIndex(chunk.posX, chunk.posY), chunk);
    }

    /**
     * Flush all stored Index-Chunk-Pairs
     */
    public void flush() {
        chunkPos = new ConcurrentHashMap<>();
    }

    /**
     * Retrieve the Chunk in which the specified coordinates lie by looking up the
     * unique index of the pair of coordinates in the HashMap
     *
     * @param posX x-Coordinate in World-Coordinates
     * @param posY y-Coordinate in World-Coordinates
     * @return Chunk of the coordinates
     */
    public Chunk getChunkByCoordinate(int posX, int posY) {
        int x = posX / chunkDimensions;
        int y = posY / chunkDimensions;

        return chunkPos.get(getIndex(x, y));
    }

    /**
     * Create a List of Chunks in range of the specified Chunk and creates chunks in reach if they don't exist
     *
     * @param target Origin Chunk
     * @param radius Radius around the origin
     * @return List of Chunks in range
     */
    public List<Chunk> getChunksInReach(Chunk target, int radius) {
        List<Chunk> chunks = new ArrayList<>();

        for (int x = 0; x <= 2 * radius; x++) {
            for (int y = 0; y <= 2 * radius; y++) {
                int chunkX = target.posX - radius + x;
                int chunkY = target.posY - radius + y;
                int index = getIndex(chunkX, chunkY);
                Chunk addition = chunkPos.get(index);
                if(addition == null){
                    addition = new Chunk(chunkX, chunkY);
                    ChunkMan.addChunk(addition);
                }
                chunks.add(addition);
            }
        }

        return chunks;
    }

    /**
     * Returns the Chunk in the specified direction from the specified origin Chunk
     * If the specified direction does not match any known directions, return null
     *
     * @param chunk     Origin Chunk
     * @param direction Direction from the origin Chunk
     * @return Chunk in that direction
     * @see Directions
     */
    public Chunk getAdjacentChunk(Chunk chunk, Directions direction) {
        switch (direction) {
            case TOP -> {
                return getChunkByCoordinate(chunk.posX, chunk.posY - 1);
            }
            case TOP_RIGHT -> {
                return getChunkByCoordinate(chunk.posX + 1, chunk.posY - 1);
            }
            case RIGHT -> {
                return getChunkByCoordinate(chunk.posX + 1, chunk.posY);
            }
            case BOTTOM_RIGHT -> {
                return getChunkByCoordinate(chunk.posX + 1, chunk.posY + 1);
            }
            case BOTTOM -> {
                return getChunkByCoordinate(chunk.posX, chunk.posY + 1);
            }
            case BOTTOM_LEFT -> {
                return getChunkByCoordinate(chunk.posX - 1, chunk.posY + 1);
            }
            case LEFT -> {
                return getChunkByCoordinate(chunk.posX - 1, chunk.posY);
            }
            case TOP_LEFT -> {
                return getChunkByCoordinate(chunk.posX - 1, chunk.posY - 1);
            }
        }
        return null;
    }

    /**
     * Returns every Chunk that is directly adjacent to the given chunk
     *
     * @param chunk     Origin Chunk
     * @return Chunks around given Chunk
     */
    public Chunk[] getAdjacentChunks(Chunk chunk){
        Chunk[] adjacentChunks = new Chunk[8];

        adjacentChunks[0] = getChunkByCoordinate(chunk.posX, chunk.posY - 1);
        adjacentChunks[1] = getChunkByCoordinate(chunk.posX + 1, chunk.posY - 1);
        adjacentChunks[2] = getChunkByCoordinate(chunk.posX + 1, chunk.posY);
        adjacentChunks[3] = getChunkByCoordinate(chunk.posX + 1, chunk.posY + 1);
        adjacentChunks[4] = getChunkByCoordinate(chunk.posX, chunk.posY + 1);
        adjacentChunks[5] = getChunkByCoordinate(chunk.posX - 1, chunk.posY + 1);
        adjacentChunks[6] = getChunkByCoordinate(chunk.posX - 1, chunk.posY);
        adjacentChunks[7] = getChunkByCoordinate(chunk.posX - 1, chunk.posY - 1);

        return adjacentChunks;
    }
}
