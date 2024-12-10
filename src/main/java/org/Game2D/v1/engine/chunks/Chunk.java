package org.Game2D.v1.engine.chunks;

import org.Game2D.v1.engine.objects.GameObject;

import java.util.ArrayList;
import java.util.List;

public class Chunk {

    public final int chunkX, chunkY;

    public final int posX, posY;

    public Chunk(int chunkX, int chunkY, int posX, int posY) {
        this.chunkX = chunkX;
        this.chunkY = chunkY;

        this.posX = posX;
        this.posY = posY;
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

    public void update() {
        for (GameObject gameObject : gameObjects) {
            gameObject.update();
        }
    }

}
