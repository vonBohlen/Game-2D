package org.Game2D.engine.graphics.loops;

import org.Game2D.engine.chunks.managers.ChunkMan;
import org.Game2D.engine.data.disk.conf.ConfProvider;
import org.Game2D.engine.graphics.Camera;
import org.Game2D.engine.graphics.CameraReplacement;
import org.Game2D.engine.graphics.opengl.GLFWManager;
import org.Game2D.engine.graphics.opengl.Window;
import org.Game2D.tools.debug.DebugScreen;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class RenderLoopReplacement implements Runnable {

    public final UUID UUID = java.util.UUID.randomUUID();

    public static int TARGET_FPS = 0;

    private Thread renderThread;

    private boolean exit = false;
    private boolean run = false;

    public Window window;

    public final ConcurrentHashMap<UUID, CameraReplacement> cameras = new ConcurrentHashMap<>(10);

    public void initialize() {
        GLFWManager.initGLFW();
        window = new Window("test");
    }


    public void start() {
        if (!GLFWManager.isGLFW_INIT() || window == null) return;

        TARGET_FPS = ConfProvider.getConfValueAsInt("game2d.graphics.target_fps");

        renderThread = new Thread(this);
        renderThread.start();
    }

    @Override
    public void run() {
        if (renderThread == null) return;

        double drawInterval = (double) 1000000000 / TARGET_FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;
        long startTime;
        long frameTime;

        while (!exit) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if (delta >= 1) {
                startTime = System.nanoTime();

                renderFramePerCamera();

                frameTime = System.nanoTime() - startTime;
                DebugScreen.updateFrameTime(frameTime);

                delta--;
                drawCount++;
            }

            if (timer >= 1000000000) {
                DebugScreen.updateFPS(drawCount);
                drawCount = 0;
                timer = 0;
            }

            // Small pause to reduce CPU usage
            if (delta < 0.5) {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }

        }
    }

    private void renderFramePerCamera() {
        boolean renderHitBoxes = ConfProvider.getConfValueAsBool("game2d.debug.graphics.render_hitboxes");
        boolean renderChunkBorders = ConfProvider.getConfValueAsBool("game2d.debug.graphics.render_chunk_borders");
        for (Map.Entry<UUID, CameraReplacement> entry : cameras.entrySet()) {
            //ChunkMan.setRenderDataByChunk(entry.getValue().getViewportChunk(), renderHitBoxes, renderChunkBorders);
        }
    }

}
