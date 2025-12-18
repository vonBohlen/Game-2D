package org.Game2D.engine.core.managers;

import org.Game2D.engine.chunks.ChunkMan;
import org.Game2D.engine.core.handlers.DataHand;
import org.Game2D.engine.debug.DebugScreen;
import org.Game2D.engine.utils.ConfProvider;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

/**
 * Render Manager<br>
 * Handles the rendering of <code>GameObjects</code>
 */
public class RenderMan extends JPanel implements Runnable {

    Thread renderThread;

    private boolean exit = false;
    private boolean run = true;

    Camera camera;

    /**
     * Instantiate a new <code>RenderManager</code>
     */
    public RenderMan() {

        //confPanel();
        confPanel();
        // Warum wird confPanel() zwei Mal gecalled?
        // Gute Frage, keine Ahnung
    }

    public void initializeCamera(){
        camera = new Camera(0,0,1440);
    }

    /**
     * Set up the screen
     */
    private void confPanel() {

        this.setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize());

        this.setBackground(Color.BLACK);

        this.setDoubleBuffered(true);

        this.addKeyListener(DataHand.keyHand);

        this.setFocusable(true);

    }

    /**
     * Create a new render thread
     */
    public void startRenderLoop() {

        renderThread = new Thread(this);

        renderThread.start();

    }

    /**
     * Executed by the render thread<br>
     * Waits until the time is right to maintain a constant
     * update rate
     */
    @Override
    public void run() {

        while (renderThread != null && !exit) {

            double drawInterval = (double) 1000000000 / Integer.parseInt(ConfProvider.getConf(DataHand.confPath).getProperty("game2d.core.fps"));
            double delta = 0;
            long lastTime = System.nanoTime();
            long currentTime;
            long timer = 0;
            int drawCount = 0;
            long startTime;
            long frameTime;

            while (run) {

                currentTime = System.nanoTime();

                delta += (currentTime - lastTime) / drawInterval;
                timer += (currentTime - lastTime);
                lastTime = currentTime;

                if (delta >= 1) {

                    startTime = System.nanoTime();

                    //renderFrame();
                    repaint();

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

            }
        }

    }

    /**
     * Implement the rendering of GameObjects
     *
     * @param g the <code>Graphics</code> object to protect
     */
    @Override
    public void paintComponent(Graphics g) {

        // Hitbox visualization
        boolean renderHitBoxes = Objects.requireNonNull(ConfProvider.getConf(DataHand.confPath)).getProperty("game2d.render.hitboxes").equals("true");

        // render chunks that have objects in them
        boolean renderActiveChunks = Objects.requireNonNull(ConfProvider.getConf(DataHand.confPath)).getProperty("game2d.render.activechunks").equals("true");

        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        g2.setColor(Color.magenta);

        // draw each object that is in render distance
        ChunkMan.renderByChunk(Camera.renderUpdate(), g2, renderHitBoxes, renderActiveChunks);

        DebugScreen.draw(g2);

        g2.dispose();
    }

    /**
     * Temporarily pause the rendering thread
     */
    public void freeze() {
        run = false;
    }

    /**
     * Resume the rendering thread after it has been paused
     */
    public void resume() {
        run = true;
    }

    /**
     * Pause the rendering thread and exits cleanly
     */
    public void exit() {
        freeze();
        exit = true;
    }

}
