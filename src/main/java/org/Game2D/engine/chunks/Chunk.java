package org.Game2D.engine.chunks;

import org.Game2D.engine.core.handlers.DataHand;
import org.Game2D.engine.objects.GameObject;

import java.util.LinkedList;
import java.util.List;

public class Chunk {

    public int[] position = new int[2];

    Chunk(int posX, int posY) {
        position[0] = posX;
        position[1] = posY;
    }

    private final List<GameObject> objects = new LinkedList<>();

    public boolean addGameObject(GameObject object) {
        if (DataHand.getGameObjs().contains(object)) {
            objects.add(object);
            return true;
        }
        return false;
    }

    public void removeGameObject(GameObject object) {
        objects.remove(object);
    }

    private void cleanUP() {
        List<GameObject> parrentList = DataHand.getGameObjs();
        objects.removeIf(object -> !parrentList.contains(object));
    }

    public void update() {
        cleanUP();
        for (GameObject object : objects) {
            object.update();
        }
    }

    public void setRenderData() {
        //Do stuff
    }


}
