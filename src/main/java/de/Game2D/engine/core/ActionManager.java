package de.Game2D.engine.core;

import de.Game2D.engine.objects.GameObject;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ActionManager implements Runnable {

    private Thread actionThread;
    private final Instance instance;

    private final List<GameObject> gameObjects = new ArrayList<>();
    private int gameTick = 0;

    public ActionManager(Instance i) {
        instance = i;
    }

    protected void startActionThread() {
        actionThread = new Thread(this);
        actionThread.start();
    }

    @Override
    public void run() {
        while (actionThread != null) {
            int tps = Integer.parseInt(PropertiesManager.getSettings().getProperty("game2d.core.tps"));
            double drawInterval = 1000000000 / tps;
            double delta = 0;
            long lastTime = System.nanoTime();
            long currentTime;
            long timer = 0;
            int drawCount = 0;

            while (actionThread != null) {

                currentTime = System.nanoTime();

                delta += (currentTime - lastTime) / drawInterval;
                timer += (currentTime - lastTime);
                lastTime = currentTime;

                if (delta >= 1) {
                    gameTick++;
                    update();
                    delta--;
                    drawCount++;
                }

                if (timer >= 1000000000) {
                    instance.getDebugDisplay().updateTPS(drawCount);
                    drawCount = 0;
                    timer = 0;
                }

                if (gameTick >= tps) gameTick = 0;
            }
        }
    }

    private void update() {
        for (GameObject go : gameObjects) {
            go.update();
        }
    }


    public GameObject checkCollision(GameObject go, Rectangle position) {
        for (GameObject current : gameObjects) {
            if (current.getCollisionActivated() && !current.equals(go) && position.intersects(current.hitBox)) return current;
        }
        return null;
    }

    public void registerGameObject(GameObject go) {
        gameObjects.add(go);
    }

    public void removeGameObject(GameObject go) {
        gameObjects.removeIf(gameObject -> gameObject == go);
    }

    public List<GameObject> getGameObjects() {
        return gameObjects;
    }


    public int getGameTick() {
        return gameTick;
    }

}
