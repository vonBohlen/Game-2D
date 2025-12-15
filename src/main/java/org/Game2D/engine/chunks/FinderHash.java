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
        //f: Z² -> N f is bijection that assigns a number in N to each integer coordinate
        if (x > Math.abs(y)) {
            return (2 * x) * (2 * x) - x + y; // Absolut dunkle Magie einfach
        } else if (y > 0 && y >= Math.abs(x)) {
            return (2 * y) * (2 * y) - y + x;
        } else if (x <= -Math.abs(y)) {
            return (2 * x) * (2 * x) - 3 * x - y;
        } else {
            return (2 * y) * (2 * y) - 3 * y + x;
        }
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
     * @param posX x-Coordinate
     * @param posY y-Coordinate
     * @return Chunk of the coordinates
     */
    public Chunk getChunkByCoordinate(int posX, int posY) {
        int x = posX / chunkDimensions;
        int y = posY / chunkDimensions;

        return chunkPos.get(getIndex(x, y));
    }

    /**
     * Create a List of Chunks in range of the specified Chunk
     *
     * @param target Origin Chunk
     * @param radius Radius around the origin
     * @return List of Chunks in range
     */
    public List<Chunk> getChunksInReach(Chunk target, int radius) {
        List<Chunk> chunks = new ArrayList<>();

        for (int x = 0; x < 2 * radius; x++) {
            for (int y = 0; y < 2 * radius; y++) {
                chunks.add(getChunkByCoordinate(target.posX - radius + x, target.posY - radius + y));
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
}
