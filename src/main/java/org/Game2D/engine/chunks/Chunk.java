package org.Game2D.engine.chunks;

import org.Game2D.engine.objects.GameObject;

import java.util.ArrayList;
import java.util.List;

public class Chunk {

    public final int ID;

    public Chunk(int id) {
        ID = id;
    }

    private final List<GameObject> gameObjects = new ArrayList<>();

    public List<GameObject> getGameObjects() {
        return gameObjects;
    }

    public void addGameObject(GameObject gameObject) {
        if (!gameObjects.contains(gameObject)) gameObjects.add(gameObject);
    }

    public void removeGameObject(GameObject gameObject) {
        gameObjects.removeIf(current -> current == gameObject);
    }

}
