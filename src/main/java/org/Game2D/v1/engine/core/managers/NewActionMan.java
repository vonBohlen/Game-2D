package org.Game2D.v1.engine.core.managers;

import org.Game2D.v1.engine.chunks.Chunk;
import org.Game2D.v1.engine.core.handlers.DataHand;
import org.Game2D.v1.engine.objects.GameObject;
import org.Game2D.v1.engine.utils.ConfProvider;
import org.Game2D.v1.engine.debug.DebugScreen;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class NewActionMan implements Runnable {

    private Thread actionThread;

    private boolean run = true;
    private static int gameTick = 0;

    private final static List<Chunk> loadedChunks = new ArrayList<>();
    private final static List<Chunk> forceLoadedChunks = new ArrayList<>();
    private final static List<Chunk> forceRenderedChunks = new ArrayList<>();

    public void startGameLoop() {

        actionThread = new Thread(this);

        actionThread.start();

    }

    @Override
    public void run() {

        while (actionThread != null) {

            int tps = Integer.parseInt(ConfProvider.getConf(DataHand.confPath).getProperty("game2d.core.tps"));
            double updateInterval = 1000000000 / tps;
            double delta = 0;
            long lastTime = System.nanoTime();
            long currentTime;
            long timer = 0;
            int updateCount = 0;

            while (run) {

                currentTime = System.nanoTime();

                delta += (currentTime - lastTime) / updateInterval;
                timer += (currentTime - lastTime);
                lastTime = currentTime;

                if (delta >= 1) {

                    gameTick++;

                    update();
                    delta--;
                    updateCount++;

                }

                if (timer >= 1000000000) {

                    DebugScreen.updateTPS(updateCount);

                    updateCount = 0;
                    timer = 0;

                }

                if (gameTick >= tps) gameTick = 0;

            }

        }

    }

    private void update() {

        for (Chunk chunk : loadedChunks ) {
            chunk.update();
        }

        for (Chunk chunk : forceLoadedChunks ) {
            chunk.update();
        }

    }

    public static GameObject checkCollision(GameObject go, Rectangle position) {

        List<GameObject> gameObjects = DataHand.getGameObjs();

        for (GameObject current : gameObjects) {

            if (current.getCollisionActivated() && !current.equals(go) && position.intersects(current.hitBox)) return current;

        }

        return null;

    }

    protected void freeze() {
        run = false;
    }

    protected void resume() {
        run = true;
    }


    public static int getGameTick() {
        return gameTick;
    }

}
