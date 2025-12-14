package org.Game2D.engine.chunks;

import org.Game2D.engine.core.handlers.DataHand;
import org.Game2D.engine.objects.GameObject;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class Chunk {

    public int[] position = new int[2];

    Chunk(int posX, int posY) {
        position[0] = posX;
        position[1] = posY;
    }

    //private final List<GameObject> objects = new LinkedList<>();
    private final HashMap<UUID, GameObject> objects = new HashMap<>();

    public boolean addGameObject(GameObject object) {
        if (DataHand.getGameObjs().contains(object)) {
            objects.put(object.uuid, object);
            return true;
        }
        return false;
    }

    public void removeGameObject(GameObject object) {
        objects.remove(object.uuid);
    }

    private void cleanUp() {
        List<GameObject> parrentList = DataHand.getGameObjs();
        //objects.removeIf(object -> !parrentList.contains(object));
        for(GameObject i : parrentList) {
            if(!parrentList.contains(i)) {objects.remove(i.uuid);}
        }
    }

    public void update() {
        cleanUp();
        for (UUID object_uuid: objects.keySet()) {
            objects.get(object_uuid).update();
        }
    }

    public void setRenderData() {
        //Do stuff
    }


}
