package org.Game2D.engine.chunks;

import org.Game2D.engine.objects.GameObject;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Chunk class that stores a HashMap of GameObjects
 * Used by the ChunkManager
 */
public class Chunk {
    public final UUID uuid;
    //private final List<GameObject> objects = new LinkedList<>();
    /**
     * HashMap of GameObjects in this Chunk identified by their UUID
     */
    private final ConcurrentHashMap<UUID, GameObject> objects = new ConcurrentHashMap<>();
    public int posX;
    public int posY;

    /**
     * Create a new Chunk with the specified coordinates
     *
     * @param posX x-Coordinate
     * @param posY y-Coordinate
     */
    public Chunk(int posX, int posY) {
        this.posX = posX;
        this.posY = posY;

        uuid = UUID.randomUUID();
    }

    /**
     * Add a GameObject to this Chunk
     *
     * @param object GameObject to be added
     */
    public void addGameObject(GameObject object) {
        objects.put(object.uuid, object);
        ChunkMan.registerObject(object, this);
    }

    /**
     * Remove a GameObject from this Chunk
     *
     * @param object Object to be removed
     */
    public void removeGameObject(GameObject object) {
        objects.remove(object.uuid);
        ChunkMan.unregisterObject(object);
    }

    /**
     * Update all GameObjects in the Chunk
     */
    public void update() {
        for (GameObject object : objects.values()) {
            object.update();
        }
    }

    /**
     * Not implemented yet
     */
    public void setRenderData() {
        //TODO
    }


}
