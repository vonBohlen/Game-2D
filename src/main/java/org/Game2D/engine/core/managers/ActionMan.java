package org.Game2D.engine.core.managers;

import org.Game2D.engine.chunks.Chunk;
import org.Game2D.engine.chunks.ChunkMan;
import org.Game2D.engine.chunks.ObjectTransferMan;
import org.Game2D.engine.core.handlers.DataHand;
import org.Game2D.engine.debug.DebugScreen;
import org.Game2D.engine.objects.GameObject;
import org.Game2D.engine.utils.ConfProvider;

import java.awt.*;
import java.util.List;

/**
 * Action Manager
 * Handles GameObject updating with the Gameloop
 * and general timing
 */
public class ActionMan implements Runnable {

    private static int gameTick = 0;
    /**
     * Thread executing GameObject updates
     */
    private Thread actionThread;
    private boolean run = true;
    private boolean exit = false;

    /**
     * Temporary solution
     * To be replaced once the chunk system is fully implemented
     *
     * @param go
     * @param position
     * @return null if no collision is found, otherwise return the collision object
     */
    public static GameObject checkCollision(GameObject go, Rectangle position) {

        ObjectTransferMan.transferAbs(go, position.x, position.y);

        List<GameObject> gameObjects = DataHand.getGameObjs();

        for (GameObject current : gameObjects) {

            if (current.getCollisionActivated() && !current.equals(go) && go.objectLayer == current.objectLayer && position.intersects(current.hitBox))
                return current;

        }

        return null;

    }

    public static int getGameTick() {
        return gameTick;
    }

    /**
     * Start the Gameloop in a seperate thread
     */
    public void startGameLoop() {

        actionThread = new Thread(this);

        actionThread.start();

    }

    /**
     * Times the updating of GameObjects and calculating TPS
     */
    @Override
    public void run() {

        while (actionThread != null && !exit) {

            int tps = Integer.parseInt(ConfProvider.getConf(DataHand.confPath).getProperty("game2d.core.tps"));
            double updateInterval = 1000000000 / tps;
            double delta = 0;
            long lastTime = System.nanoTime();
            long currentTime;
            long timer = 0;
            int updateCount = 0;
            long startTime;
            long tickTime;

            while (run) {

                currentTime = System.nanoTime();

                delta += (currentTime - lastTime) / updateInterval;
                timer += (currentTime - lastTime);
                lastTime = currentTime;

                if (delta >= 1) {

                    gameTick++;

                    startTime = System.nanoTime();

                    update();

                    tickTime = System.nanoTime() - startTime;

                    DebugScreen.updateTickTime(tickTime);

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

    /**
     * Actual method calling the Chunks to update all the GameObjects contained within
     */
    private void update() {
        Chunk target = ChunkMan.ChunkFromCoordinates(0, 0);
        ChunkMan.updateByChunk(target);
    }

    /**
     * Freeze the Gameloop and pause GameObject updates
     */
    public void freeze() {
        run = false;
    }

    /**
     * Resume the Gameloop and continue GameObject updates
     */
    public void resume() {
        run = true;
    }

    /**
     * Stop the Gameloop and exit
     */
    public void exit() {
        freeze();
        exit = true;
    }

}
