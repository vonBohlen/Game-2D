package org.Game2D.v1.engine.chunks;

import org.Game2D.v1.engine.objects.GameObject;
import org.Game2D.v1.engine.objects.advanced.Entity;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ChunkManager {

    private static volatile boolean lockAcquired = false;

    private static final ArrayList<Long> improQueue = new ArrayList<>();


    private static final List<Chunk> chunks = new ArrayList<>();


    //Spin lock

    private static void acquirerLock(long id) {
        addToQueue(id);
        while (lockAcquired && getFirstInQueue() == id) {
            Thread.onSpinWait();
        }
        removeFirstInQueue();
        lockAcquired = true;
    }

    private static void addToQueue(Long id){
        improQueue.add(id);
    }

    private static long getFirstInQueue(){
        return improQueue.get(0);
    }

    private static void removeFirstInQueue(){
        improQueue.remove(0);
    }

    private static void dropLock() {
        lockAcquired = false;
    }


    //addChunk (chunk)

    public static void addChunk(Chunk chunk) {
        acquirerLock(Thread.currentThread().getId());
        chunks.add(chunk);
        dropLock();
    }

    public static void addChunks(List<Chunk> newChunks) {
        acquirerLock(Thread.currentThread().getId());
        chunks.addAll(newChunks);
        dropLock();
    }


    //TODO: removeChunk (chunk)

    public void removeGameChunk(Chunk chunk) {
        acquirerLock(Thread.currentThread().getId());
        chunks.remove(chunk);
        dropLock();
    }

    public void removeGameChunks(List<Chunk> oldChunks) {
        acquirerLock(Thread.currentThread().getId());
        chunks.removeAll(oldChunks);
        dropLock();
    }


    //TODO: TransferEntity (entity, origin, target)

    public static void transferEntity(Entity entity, Chunk origin, Chunk target) {
        origin.removeGameObject(entity);
        target.addGameObjects(entity);
    }

    public static void transferEntities(List<GameObject> entities, Chunk origin, Chunk target) {
        origin.removeGameObjects(entities);
        target.addGameObjects(entities);
    }

    //TODO: existById (index, value)

    public static boolean existsById(int index, int value) {
        acquirerLock(Thread.currentThread().getId());
        for (Chunk chunk : chunks) {
            if (chunk.getId().get(index) == value) {
                dropLock();
                return true;
            }
        }
        dropLock();
        return false;
    }


    //TODO: existByPosition (posX, posY)

    public static boolean existsByPosition(int posX, int posY) {
        acquirerLock(Thread.currentThread().getId());
        for (Chunk chunk : chunks) {
            if (chunk.getId().get(0) == posX && chunk.getId().get(1) == posY) {
                dropLock();
                return true;
            }
        }
        dropLock();
        return false;
    }


    //TODO: getChunkById (index, value)

    public static Chunk getChunkById(int index, int value) {
        acquirerLock(Thread.currentThread().getId());
        for (Chunk chunk : chunks) {
            if (chunk.getId().get(index) == value) {
                dropLock();
                return chunk;
            }
        }
        dropLock();
        return null;
    }


    //TODO: getChunkByPosition (posX, posY)

    public static Chunk getChunkByPosition(int posX, int posY) {
        acquirerLock(Thread.currentThread().getId());
        for (Chunk chunk : chunks) {
            if (chunk.getId().get(0) == posX && chunk.getId().get(1) == posY) {
                dropLock();
                return chunk;
            }
        }
        dropLock();
        return null;
    }


    //TODO: updateChunkById (index, value) /

    public static void updateChunkById(int index, int value) {
        acquirerLock(Thread.currentThread().getId());
        for (Chunk chunk : chunks) {
            if (chunk.getId().get(index) == value) {
                chunk.update();
                dropLock();
                return;
            }
        }
        dropLock();
    }

    public static void updateChunksById(int index, int value) {
        acquirerLock(Thread.currentThread().getId());
        for (Chunk chunk : chunks) {
            if (chunk.getId().get(index) == value) {
                chunk.update();
            }
        }
        dropLock();
    }


    //TODO: updateChunkByPosition (posX, posY) /

    public static void updateChunkByPosition(int posX, int posY) {
        acquirerLock(Thread.currentThread().getId());
        for (Chunk chunk : chunks) {
            if (chunk.getId().get(0) == posX && chunk.getId().get(1) == posY) {
                chunk.update();
                dropLock();
                return;
            }
        }
        dropLock();
    }


    //TODO: drawChunkById (index, value) /

    public static void drawChunkById(int index, int value, Graphics2D g2) {
        acquirerLock(Thread.currentThread().getId());
        for (Chunk chunk : chunks) {
            if (chunk.getId().get(index) == value) {
                chunk.draw(g2);
                dropLock();
                return;
            }
        }
        dropLock();
    }

    public static void drawChunksById(int index, int value, Graphics2D g2) {
        acquirerLock(Thread.currentThread().getId());
        for (Chunk chunk : chunks) {
            if (chunk.getId().get(index) == value) {
                chunk.draw(g2);
            }
        }
        dropLock();
    }


    //TODO: drawChunkByPosition (posX, posY) /

    public static void drawChunkByPosition(int posX, int posY, Graphics2D g2) {
        acquirerLock(Thread.currentThread().getId());
        for (Chunk chunk : chunks) {
            if (chunk.getId().get(0) == posX && chunk.getId().get(1) == posY) {
                chunk.draw(g2);
                dropLock();
                return;
            }
        }
        dropLock();
    }


    public static List<Chunk> getChunks() {
        return new ArrayList<>(chunks);
    }

}
