package org.Game2D.v1.engine.chunks;

import org.Game2D.v1.engine.objects.GameObject;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class Chunk {

    private volatile boolean lockAcquired = false;

    private final Vector<Integer> id;

    private final List<GameObject> gameObjects = new ArrayList<>();

    public Chunk(Vector<Integer> id, List<GameObject> initialObjects) {
        this.id = id;

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
        gameObjects.add(gameObject);
        dropLock();
    }

    public void addGameObject(List<GameObject> gameObjects) {
        acquirerLock();
        this.gameObjects.addAll(gameObjects);
        dropLock();
    }

    public void removeGameObject(GameObject gameObject) {
        acquirerLock();
        gameObjects.removeIf(current -> current == gameObject);
        dropLock();
    }

    public void removeGameObjects(List<GameObject> gameObjects) {
        acquirerLock();
        this.gameObjects.removeAll(gameObjects);
        dropLock();
    }

    public void update() {
        acquirerLock();
        for (GameObject gameObject : gameObjects) {
            gameObject.update();
        }
        dropLock();
    }

    public void draw(Graphics2D g2) {
        acquirerLock();
        for (GameObject gameObject : gameObjects) {
            gameObject.draw(g2);
        }
        dropLock();
    }

    public List<GameObject> getGameObjects() {
        return new ArrayList<>(gameObjects);
    }

    public Vector<Integer> getId() {
        return new Vector<>(id);
    }

}
