package de.Alienlive.games.Game2D.core.manager;

import de.Alienlive.games.Game2D.core.Main;
import de.Alienlive.games.Game2D.entities.entity.Entity;

import java.util.ArrayList;
import java.util.List;

public class ActionManager implements Runnable {


    private final List<Entity> entities = new ArrayList<>();

    private boolean updateUI = false;

    Thread actionThread;

    public void startActionThread() {
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
                    Main.getDebugDisplay().updateTPS(drawCount);
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

    public boolean checkCollisionForEntity(Entity e) {
        for (Entity current : entities) {
            if (current != e && e.box.intersects(current.box)) return true;
        }
        return false;
    }

    private void updateUI() {

    }

    public void registerEntity(Entity e) {
        entities.add(e);
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
