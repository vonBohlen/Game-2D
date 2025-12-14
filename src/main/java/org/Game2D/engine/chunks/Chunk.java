package org.Game2D.engine.chunks;

import org.Game2D.engine.core.handlers.DataHand;
import org.Game2D.engine.objects.GameObject;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class Chunk {

    public final UUID uuid;
    //private final List<GameObject> objects = new LinkedList<>();
    private final HashMap<UUID, GameObject> objects = new HashMap<>();
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
     * @return true if possible, false if not possible
     */
    public boolean addGameObject(GameObject object) {
        if (DataHand.getGameObjs().contains(object)) {
            objects.put(object.uuid, object);
            ChunkMan.registerObject(object, this);
            return true;
        }
        return false;
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

    private void cleanUp() {
        List<GameObject> parrentList = DataHand.getGameObjs();
        //objects.removeIf(object -> !parrentList.contains(object));
        for (GameObject i : parrentList) {
            if (!parrentList.contains(i)) {
                objects.remove(i.uuid);
            }
        }
    }

    public void update() {
        cleanUp();
        for (UUID object_uuid : objects.keySet()) {
            objects.get(object_uuid).update();
        }
    }

    public void setRenderData() {
        //Do stuff
    }


}
