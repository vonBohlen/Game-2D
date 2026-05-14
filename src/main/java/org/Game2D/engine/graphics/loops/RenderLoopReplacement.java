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
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class RenderLoopReplacement implements Runnable {

    public final UUID UUID = java.util.UUID.randomUUID();

    public static int TARGET_FPS = 0;

    private Thread renderThread;

    private boolean exit = false;
    private boolean run = false;

    public Window window;

    public final ConcurrentHashMap<UUID, CameraReplacement> cameras = new ConcurrentHashMap<>(10);

    private ByteBuffer buffer;
    public void initialize() {
        BufferedImage image = (BufferedImage) AssetManager.getAsset("flappy_assets/bird/yellowbird-midflap.png");
        buffer = ByteBuffer.allocate(image.getWidth()*image.getHeight()*4);

        byte[] bufferArray = new byte[image.getWidth()*image.getHeight()*4];
        int[] imageArray = new int[image.getWidth()*image.getHeight()];
        image.getRGB(0,0, image.getWidth(), image.getHeight(), imageArray, 0, image.getWidth());

        for(int i = 0; i < imageArray.length; i++){
            byte r = (byte)(imageArray[i] & 0x00000011);
            byte g = (byte)((imageArray[i] & 0x00001100) >> 8);
            byte b = (byte)((imageArray[i] & 0x00110000) >> 16);
            byte a = (byte)((imageArray[i] & 0x11000000) >> 24);

            bufferArray[4 * i] = r;
            bufferArray[4 * i + 1] = g;
            bufferArray[4 * i + 2] = b;
            bufferArray[4 * i + 3] = a;
        }

        buffer.put(bufferArray);
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
        float r = 0.75f, g = 0.25f, b = 0;
        float dc = 0.01f;
        r += dc;
        g += dc;
        glClearColor(r, g, b, 0.0f);


        glBegin(GL_POLYGON);
        glColor3f(r,g,0.5f);
        glVertex2i(100, 100);
        glVertex2i(100, 200);
        //glColor3f(1,0.5f,0);
        glVertex2i(200, 200);
        glVertex2i(200, 100);
        glEnd();
        glFlush();

        glfwSwapBuffers(window.getWindowID()); // swap the color buffers

        // Poll for window events. The key callback above will only be
        // invoked during this call.
        glfwPollEvents();
    }

}
