package org.Game2D.engine.chunks;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

public class FinderHash {

    private ConcurrentHashMap<Integer, Chunk> chunkPos;
    private final int chunkDimensions;

    FinderHash(int chunkDim){
        chunkPos = new ConcurrentHashMap<>();
        chunkDimensions = chunkDim;
    }

    private int getIndex(int x, int y){
        //f: Z² -> N f is bijection that assigns a number in N to each integer coordinate
        if(x > Math.abs(y)){
            return (2 * x) * (2 * x) - x + y;
        }
        else if(y > 0 && y >= Math.abs(x)){
            return (2 * y) * (2 * y) - y + x;
        }
        else if(x <= -Math.abs(y)){
            return (2 * x) * (2 * x) - 3 * x - y;
        }
        else{
            return (2 * y) * (2 * y) - 3 * y + x;
        }
    }

    public void addChunk(Chunk chunk){
        chunkPos.put(getIndex(chunk.posX, chunk.posY), chunk);
    }

    public void flush(){
        chunkPos = new ConcurrentHashMap<>();
    }

    public Chunk getChunkByCoordinate(int posX, int posY){
        int x = posX / chunkDimensions;
        int y = posY / chunkDimensions;

        return chunkPos.get(getIndex(x, y));
    }

    public List<Chunk> getChunksInReach(Chunk target, int radius){
        List<Chunk> chunks = new ArrayList<>();

        for(int x = 0; x < 2 * radius; x++){
            for(int y = 0; y < 2 * radius; y++){
                chunks.add(getChunkByCoordinate(target.posX - radius + x, target.posY - radius + y));
            }
        }

        return chunks;
    }

    public Chunk getAdjacentChunk(Chunk chunk, Directions direction){
        switch (direction){
            case TOP -> { return getChunkByCoordinate(chunk.posX, chunk.posY - 1); }
            case TOP_RIGHT -> { return getChunkByCoordinate(chunk.posX + 1, chunk.posY - 1); }
            case RIGHT -> { return getChunkByCoordinate(chunk.posX + 1, chunk.posY); }
            case BOTTOM_RIGHT -> { return getChunkByCoordinate(chunk.posX + 1, chunk.posY + 1); }
            case BOTTOM -> { return getChunkByCoordinate(chunk.posX, chunk.posY + 1); }
            case BOTTOM_LEFT -> { return getChunkByCoordinate(chunk.posX - 1, chunk.posY + 1); }
            case LEFT -> { return getChunkByCoordinate(chunk.posX - 1, chunk.posY); }
            case TOP_LEFT -> { return getChunkByCoordinate(chunk.posX - 1, chunk.posY - 1); }
        }
        return null;
    }
}
