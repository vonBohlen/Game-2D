package de.Alienlive.games.Game2D.core;

import de.Alienlive.games.Game2D.objects.entities.entity.Entity;
import de.Alienlive.games.Game2D.objects.objects.Object;
import de.Alienlive.games.Game2D.test.Main;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ActionManager implements Runnable {


    private final List<Entity> entities = new ArrayList<>();
    private final List<Object> objects = new ArrayList<>();

    private boolean updateUI = false;

    Thread actionThread;

    private final Instance instance;

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

            double drawInterval = 1000000000 / Integer.parseInt(PropertiesManager.getSettings().getProperty("game2d.core.tps"));
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
                    if (!updateUI) update();
                    else updateUI();
                    delta--;
                    drawCount++;
                }

                if (timer >= 1000000000) {
                    instance.getDebugDisplay().updateTPS(drawCount);
                    drawCount = 0;
                    timer = 0;
                }
            }
        }
    }

    private void update() {
        for (Entity ce : entities) {
            ce.update();
        }
    }

    public boolean checkCollisionForEntity(Entity e, Rectangle r) {
        for (Entity current : entities) {
            if (!current.equals(e) && r.intersects(current.box)) return true;
        }
        return false;
    }

    private void updateUI() {

    }

    public void registerEntity(Entity e) {
        entities.add(e);
    }

    public void registerObject(Object o) {
        objects.add(o);
    }

    public void removeEntity(Entity e) {
        entities.removeIf(entity -> entity == e);
    }

    public List<Entity> getEntities() {
        return entities;
    }

    public void setUpdateUI(boolean updateUI) {
        this.updateUI = updateUI;
    }

}
