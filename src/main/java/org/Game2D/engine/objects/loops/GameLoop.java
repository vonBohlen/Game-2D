/**
 * /engine/objects/loops/GameLoop.java
 *
 * Handles GameObject updating with the GameLoop and general timing
 *
 * Copyright (C) 2026 Christian von Bohlen, Luca Mergel, J. K.
 */

package org.Game2D.engine.objects.loops;

import lombok.Getter;
import org.Game2D.engine.chunks.Chunk;
import org.Game2D.engine.chunks.managers.ChunkMan;
import org.Game2D.engine.chunks.utils.data.Directions;
import org.Game2D.engine.data.disk.conf.ConfProvider;
import org.Game2D.engine.objects.GameObject;
import org.Game2D.tools.debug.DebugScreen;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * GameLoop<br>
 * Handles GameObject updating with the GameLoop
 * and general timing
 */
public class GameLoop implements Runnable {

    public static int TARGET_TPS = 60;

    @Getter
    private static int gameTick = 0;

    /**
     * Thread executing GameObject updates
     */
    private Thread actionThread;
    private boolean run = true;
    private boolean exit = false;

    // Sleep optimization constants
    private static final long MIN_SLEEP_TIME_NS = 1_000_000L; // 1ms
    private static final long MAX_SLEEP_TIME_NS = 16_000_000L; // 16ms (~60Hz)


    /**
     * Start the GameLoop in a separate thread
     */
    public void startGameLoop() {

        TARGET_TPS = ConfProvider.getConfValueAsInt("game2d.game_loop.target_tps");

        actionThread = new Thread(this);
        actionThread.start();
    }

    /**
     * Times the updating of GameObjects and calculating TARGET_TPS
     */
    @Override
    public void run() {
        while (actionThread != null && !exit) {

            double updateInterval = (double) 1000000000 / TARGET_TPS;
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

                if (gameTick >= TARGET_TPS) {
                    gameTick = 0;
                }

                // Sleep optimization to reduce CPU usage
                try {
                    // Calculate time until next potential update
                    long timeUntilNextUpdate = (long) ((1 - delta) * updateInterval);

                    if (timeUntilNextUpdate > MIN_SLEEP_TIME_NS) {
                        // Convert nanoseconds to milliseconds for Thread.sleep()
                        long sleepTimeMs = Math.min(
                                timeUntilNextUpdate / 1_000_000L,
                                MAX_SLEEP_TIME_NS / 1_000_000L
                        );

                        if (sleepTimeMs > 0) {
                            Thread.sleep(sleepTimeMs);
                        }
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
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
     * Temporary solution
     * To be replaced once the chunk system is fully implemented
     *
     * @param go The GameObject to check collision for
     * @param position The position to check
     * @return null if no collision is found, otherwise return the collision object
     */
    public static GameObject checkCollision(GameObject go, Rectangle position) {

        // TODO: Replace method once chunk system is complete

        Chunk chunk = ChunkMan.getChunkFromObject(go);

        List<GameObject> gameObjectCache = new ArrayList<>(chunk.objectsByLayers.get(go.objectLayer));

        // TODO: Replace by only loading adjacent chunks in the direction in which the GameObject is moving
        gameObjectCache.addAll(ChunkMan.getAdjacentChunk(chunk, Directions.TOP).objectsByLayers.get(go.objectLayer));
        gameObjectCache.addAll(ChunkMan.getAdjacentChunk(chunk, Directions.TOP_LEFT).objectsByLayers.get(go.objectLayer));
        gameObjectCache.addAll(ChunkMan.getAdjacentChunk(chunk, Directions.LEFT).objectsByLayers.get(go.objectLayer));
        gameObjectCache.addAll(ChunkMan.getAdjacentChunk(chunk, Directions.BOTTOM_LEFT).objectsByLayers.get(go.objectLayer));
        gameObjectCache.addAll(ChunkMan.getAdjacentChunk(chunk, Directions.BOTTOM).objectsByLayers.get(go.objectLayer));
        gameObjectCache.addAll(ChunkMan.getAdjacentChunk(chunk, Directions.TOP_RIGHT).objectsByLayers.get(go.objectLayer));
        gameObjectCache.addAll(ChunkMan.getAdjacentChunk(chunk, Directions.RIGHT).objectsByLayers.get(go.objectLayer));
        gameObjectCache.addAll(ChunkMan.getAdjacentChunk(chunk, Directions.BOTTOM_RIGHT).objectsByLayers.get(go.objectLayer));

        for (GameObject current : gameObjectCache) {
            if (current.collisionEnabled && !current.equals(go) &&
                    go.objectLayer == current.objectLayer && position.intersects(current.hitBox)) {
                return current;
            }
        }

        return null;
    }

    /**
     * Freeze the GameLoop and pause GameObject updates
     */
    public void freeze() {
        run = false;
    }

    /**
     * Resume the GameLoop and continue GameObject updates
     */
    public void resume() {
        run = true;
    }

    /**
     * Stop the GameLoop and exit
     */
    public void exit() {
        freeze();
        exit = true;

        // Clean up thread reference
        actionThread = null;
    }
}