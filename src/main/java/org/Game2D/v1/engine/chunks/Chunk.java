package org.Game2D.v1.engine.chunks;

import org.Game2D.v1.engine.objects.GameObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class Chunk {

    private volatile boolean lockAcquired = false;

    private Vector<Integer> id = new Vector<>(3);

    private final List<GameObject> objects = new ArrayList<>();

    public Chunk(int chunkX, int chunkY, int size, List<GameObject> initialObjects) {
        id.add(0, chunkX);
        id.add(1, chunkY);
        id.add(2, size);

        addGameObject(initialObjects);
    }

    private void acquirerLock() {
        while (lockAcquired) {
            Thread.onSpinWait();
        }
        lockAcquired = true;
    }

    private void dropLock() {
        lockAcquired = false;
    }

    public void addGameObject(GameObject gameObject) {
        acquirerLock();
        objects.add(gameObject);
        dropLock();
    }

    public void addGameObject(List<GameObject> gameObjects) {
        acquirerLock();
        objects.addAll(gameObjects);
        dropLock();
    }

    public void removeGameObject(GameObject gameObject) {
        acquirerLock();
        objects.removeIf(current -> current == gameObject);
        dropLock();
    }

    public void update() {
        acquirerLock();
        for (GameObject gameObject : objects) {
            gameObject.update();
        }
        dropLock();
    }

}
