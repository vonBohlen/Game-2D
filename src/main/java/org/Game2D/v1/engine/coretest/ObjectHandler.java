package org.Game2D.v1.engine.coretest;

import org.Game2D.v1.engine.objects.GameObject;

import java.util.HashSet;

public class ObjectHandler implements Runnable {

    private final HashSet<GameObject> gameObjects = new HashSet<>();

    protected void addGameObj(GameObject go) {
        gameObjects.add(go);
    }

    protected void removeGameObj(GameObject go) {
        gameObjects.removeIf(gameObject -> gameObject  == go);
    }

    @Override
    public void run() {

    }

}
