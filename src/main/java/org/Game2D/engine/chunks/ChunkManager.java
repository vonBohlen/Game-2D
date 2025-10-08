package org.Game2D.engine.chunks;

import org.Game2D.engine.objects.GameObject;
import org.Game2D.engine.objects.advanced.Entity;
import org.Game2D.engine.utils.SpinLock;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ChunkManager {

    private static final SpinLock lock = new SpinLock();


    private static final List<Chunk> chunks = new ArrayList<>();


    //addChunk (chunk)

    public static void addChunk(Chunk chunk) {
        long threadId = Thread.currentThread().getId();
        lock.acquirerLock(threadId);
        chunks.add(chunk);
        lock.dropLock(threadId);
    }

    public static void addChunks(List<Chunk> newChunks) {
        long threadId = Thread.currentThread().getId();
        lock.acquirerLock(threadId);
        chunks.addAll(newChunks);
        lock.dropLock(threadId);
    }


    //

    public void removeGameChunk(Chunk chunk) {
        long threadId = Thread.currentThread().getId();
        lock.acquirerLock(threadId);
        chunks.remove(chunk);
        lock.dropLock(threadId);
    }

    public void removeGameChunks(List<Chunk> oldChunks) {
        long threadId = Thread.currentThread().getId();
        lock.acquirerLock(threadId);
        chunks.removeAll(oldChunks);
        lock.dropLock(threadId);
    }


    //

    public static void transferEntity(Entity entity, Chunk origin, Chunk target) {
        long threadId = Thread.currentThread().getId();
        lock.acquirerLock(threadId);
        origin.removeGameObject(entity);
        target.addGameObjects(entity);
        lock.dropLock(threadId);
    }

    public static void transferEntities(List<GameObject> entities, Chunk origin, Chunk target) {
        long threadId = Thread.currentThread().getId();
        lock.acquirerLock(threadId);
        origin.removeGameObjects(entities);
        target.addGameObjects(entities);
        lock.dropLock(threadId);
    }

    //

    public static boolean existsById(int index, int value) {
        long threadId = Thread.currentThread().getId();
        lock.acquirerLock(threadId);
        for (Chunk chunk : chunks) {
            if (chunk.getId().get(index) == value) {
                lock.dropLock(threadId);
                return true;
            }
        }
        lock.dropLock(threadId);
        return false;
    }


    //

    public static boolean existsByPosition(int posX, int posY) {
        long threadId = Thread.currentThread().getId();
        lock.acquirerLock(threadId);
        for (Chunk chunk : chunks) {
            if (chunk.getId().get(0) == posX && chunk.getId().get(1) == posY) {
                lock.dropLock(threadId);
                return true;
            }
        }
        lock.dropLock(threadId);
        return false;
    }


    //

    public static Chunk getChunkById(int index, int value) {
        long threadId = Thread.currentThread().getId();
        lock.acquirerLock(threadId);
        for (Chunk chunk : chunks) {
            if (chunk.getId().get(index) == value) {
                lock.dropLock(threadId);
                return chunk;
            }
        }
        lock.dropLock(threadId);
        return null;
    }


    //

    public static Chunk getChunkByPosition(int posX, int posY) {
        long threadId = Thread.currentThread().getId();
        lock.acquirerLock(threadId);
        for (Chunk chunk : chunks) {
            if (chunk.getId().get(0) == posX && chunk.getId().get(1) == posY) {
                lock.dropLock(threadId);
                return chunk;
            }
        }
        lock.dropLock(threadId);
        return null;
    }


    //

    public static void updateChunkById(int index, int value) {
        long threadId = Thread.currentThread().getId();
        lock.acquirerLock(threadId);
        for (Chunk chunk : chunks) {
            if (chunk.getId().get(index) == value) {
                chunk.update();
                lock.dropLock(threadId);
                return;
            }
        }
        lock.dropLock(threadId);
    }

    public static void updateChunksById(int index, int value) {
        long threadId = Thread.currentThread().getId();
        lock.acquirerLock(threadId);
        for (Chunk chunk : chunks) {
            if (chunk.getId().get(index) == value) {
                chunk.update();
            }
        }
        lock.dropLock(threadId);
    }


    //

    public static void updateChunkByPosition(int posX, int posY) {
        long threadId = Thread.currentThread().getId();
        lock.acquirerLock(threadId);
        for (Chunk chunk : chunks) {
            if (chunk.getId().get(0) == posX && chunk.getId().get(1) == posY) {
                chunk.update();
                lock.dropLock(threadId);
                return;
            }
        }
        lock.dropLock(threadId);
    }


    //

    public static void renderChunkById(int index, int value, Graphics2D g2) {
        long threadId = Thread.currentThread().getId();
        lock.acquirerLock(threadId);
        for (Chunk chunk : chunks) {
            if (chunk.getId().get(index) == value) {
                chunk.render(g2);
                lock.dropLock(threadId);
                return;
            }
        }
        lock.dropLock(threadId);
    }

    public static void renderChunksById(int index, int value, Graphics2D g2) {
        long threadId = Thread.currentThread().getId();
        lock.acquirerLock(threadId);
        for (Chunk chunk : chunks) {
            if (chunk.getId().get(index) == value) {
                chunk.render(g2);
            }
        }
        lock.dropLock(threadId);
    }


    //

    public static void renderChunkByPosition(int posX, int posY, Graphics2D g2) {
        long threadId = Thread.currentThread().getId();
        lock.acquirerLock(threadId);
        for (Chunk chunk : chunks) {
            if (chunk.getId().get(0) == posX && chunk.getId().get(1) == posY) {
                chunk.render(g2);
                lock.dropLock(threadId);
                return;
            }
        }
        lock.dropLock(threadId);
    }


    public static List<Chunk> getChunks() {
        return new ArrayList<>(chunks);
    }

}
