package org.Game2D.engine.graphics.loops;

import org.Game2D.engine.data.disk.assets.AssetManager;
import org.Game2D.engine.data.disk.conf.ConfProvider;
import org.Game2D.engine.graphics.CameraReplacement;
import org.Game2D.engine.graphics.GLFWManager;
import org.Game2D.engine.graphics.Window;
import org.Game2D.tools.debug.DebugScreen;
import org.lwjgl.opengl.GL;

import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class RenderLoopReplacement implements Runnable {

    public final UUID UUID = java.util.UUID.randomUUID();

    public static int TARGET_FPS = 1;

    private Thread renderThread;

    private boolean exit = false;
    private boolean run = false;

    public Window window;

    public final ConcurrentHashMap<UUID, CameraReplacement> cameras = new ConcurrentHashMap<>(10);

    private ByteBuffer buffer;
    private BufferedImage image;
    public void initialize() {
        image = (BufferedImage) AssetManager.getAsset("flappy_assets/bird/yellowbird-midflap.png");
        buffer = ByteBuffer.allocateDirect(image.getWidth() * image.getHeight() * 4).order(ByteOrder.nativeOrder());

        int[] imageArray = new int[image.getWidth()*image.getHeight()];
        image.getRGB(0,0, image.getWidth(), image.getHeight(), imageArray, 0, image.getWidth());

        for(int i = 0; i < imageArray.length; i++){
            buffer.put((byte) ((imageArray[i] >> 16) & 0xFF));
            buffer.put((byte) ((imageArray[i] >> 8) & 0xFF));
            buffer.put((byte) (imageArray[i] & 0xFF));
            buffer.put((byte) ((imageArray[i] >> 24) & 0xFF));
        }

        buffer.flip();
    }


    public void start() {
       // if (!GLFWManager.isGLFW_INIT() || window == null) return;

        TARGET_FPS = 60; //ConfProvider.getConfValueAsInt("game2d.graphics.target_fps");

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

        GLFWManager.initGLFW();
        window = new Window("test");

        GL.createCapabilities();

        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glOrtho(0.0, 640, 320, 0.0, -1.0, 1.0);
        glMatrixMode(GL_MODELVIEW);

        int textureID = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, textureID);

        // Set the clear color
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

        while (!exit && !glfwWindowShouldClose(window.getWindowID())) {
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
        //boolean renderHitBoxes = ConfProvider.getConfValueAsBool("game2d.debug.graphics.render_hitboxes");
        //boolean renderChunkBorders = ConfProvider.getConfValueAsBool("game2d.debug.graphics.render_chunk_borders");
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
//        for (Map.Entry<UUID, CameraReplacement> entry : cameras.entrySet()) {
//            //ChunkMan.setRenderDataByChunk(entry.getValue().getViewportChunk(), renderHitBoxes, renderChunkBorders);
//        }

        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, image.getWidth(), image.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);

        glEnd();
        glFlush();

        glfwSwapBuffers(window.getWindowID()); // swap the color buffers

        // Poll for window events. The key callback above will only be
        // invoked during this call.
        glfwPollEvents();
    }

}
