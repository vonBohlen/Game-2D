package org.Game2D.engine.chunks;

import org.Game2D.engine.objects.GameObject;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class Chunk {

    public final UUID uuid;
    //private final List<GameObject> objects = new LinkedList<>();
    private final ConcurrentHashMap<UUID, GameObject> objects = new ConcurrentHashMap<>();
    public int posX;
    public int posY;

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

    public void update() {
        for(GameObject object : objects.values()) {
            object.update();
        }
    }

    public void setRenderData() {
        //Do stuff
    }


}
