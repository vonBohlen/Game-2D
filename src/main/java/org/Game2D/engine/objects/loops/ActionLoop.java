/**
 * Action Manager<br>
 * Handles GameObject updating with the Gameloop
 * and general timing
 * @author The Game2D contributors
 */

package org.Game2D.engine.objects.loops;

import lombok.Getter;
import org.Game2D.engine.chunks.Chunk;
import org.Game2D.engine.chunks.manager.ChunkMan;
import org.Game2D.engine.chunks.utils.data.Directions;
import org.Game2D.engine.data.runtime.DataHand;
import org.Game2D.tools.DebugScreen;
import org.Game2D.engine.objects.GameObject;
import org.Game2D.engine.io.conf.ConfProvider;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Action Manager<br>
 * Handles GameObject updating with the Gameloop
 * and general timing
 */
public class ActionLoop implements Runnable {

    @Getter
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

        //ObjectTransferMan.transferAbs(go, position.x, position.y);

        // TODO: Replace methode

        Chunk chunk = ChunkMan.getChunkFromObject(go);

        List<GameObject> gameObjectCache = new ArrayList<>(chunk.objectsByLayers);

        // TODO: Replace by only loading adjacent chunks in the direction in which the GameObject is moving
        gameObjectCache.addAll(ChunkMan.getAdjacentChunk(chunk, Directions.TOP).objectsByLayers);
        gameObjectCache.addAll(ChunkMan.getAdjacentChunk(chunk, Directions.TOP_LEFT).objectsByLayers);
        gameObjectCache.addAll(ChunkMan.getAdjacentChunk(chunk, Directions.LEFT).objectsByLayers);
        gameObjectCache.addAll(ChunkMan.getAdjacentChunk(chunk, Directions.BOTTOM_LEFT).objectsByLayers);
        gameObjectCache.addAll(ChunkMan.getAdjacentChunk(chunk, Directions.BOTTOM).objectsByLayers);
        gameObjectCache.addAll(ChunkMan.getAdjacentChunk(chunk, Directions.TOP_RIGHT).objectsByLayers);
        gameObjectCache.addAll(ChunkMan.getAdjacentChunk(chunk, Directions.RIGHT).objectsByLayers);
        gameObjectCache.addAll(ChunkMan.getAdjacentChunk(chunk, Directions.BOTTOM_RIGHT).objectsByLayers);

        for (GameObject current : gameObjectCache) {

            if (current.collisionEnabled && !current.equals(go) && go.objectLayer == current.objectLayer && position.intersects(current.hitBox))
                return current;

        }

        return null;

    }

    /**
     * Start the Gameloop in a separate thread
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
            double updateInterval = (double) 1000000000 / tps;
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
        ChunkMan.updateByChunk();
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
