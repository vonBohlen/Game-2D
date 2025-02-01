package org.Game2D.v1.engine.chunks;

import org.Game2D.v1.engine.objects.GameObject;
import org.Game2D.v1.engine.objects.advanced.Entity;

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

    //TODO: existByPosition (posX, posY)

    //TODO: getChunkById (index, value)

    //TODO: getChunkByPosition (posX, posY)

    //TODO: updateChunksById (index, value) /

    //TODO: updateChunksByPosition (posX, posY) /

    //TODO: drawChunksById (index, value) /

    //TODO: drawChunksByPosition (posX, posY) /

    public static List<Chunk> getChunks() {
        return new ArrayList<>(chunks);
    }

}
