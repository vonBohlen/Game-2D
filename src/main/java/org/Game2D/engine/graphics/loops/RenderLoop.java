/**
 * Render Manager<br>
 * Handles the rendering of <code>GameObjects</code>
 * @author The Game2D contributors
 */

package org.Game2D.engine.graphics.loops;

import org.Game2D.engine.chunks.manager.ChunkMan;
import org.Game2D.engine.data.runtime.DataHand;
import org.Game2D.engine.graphics.Camera;
import org.Game2D.engine.io.conf.ConfProvider;
import org.Game2D.tools.DebugScreen;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Objects;

/**
 * Render Manager<br>
 * Handles the rendering of <code>GameObjects</code>
 */
public class RenderLoop extends JPanel implements Runnable {

    Thread renderThread;

    private boolean exit = false;
    private boolean run = true;

    private BufferedImage frame;

    Camera camera;

    /**
     * Instantiate a new <code>RenderManager</code>
     */
    public RenderLoop() {

        confPanel();
    }

    public void initializeCamera(){
        camera = new Camera(0,0,0);
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
     * Create a new setRenderData thread
     */
    public void startRenderLoop() {

        frame = new BufferedImage(DataHand.renderLoop.getWidth(), DataHand.renderLoop.getHeight(), BufferedImage.TYPE_INT_RGB);

        renderThread = new Thread(this);

        renderThread.start();

    }

    /**
     * Executed by the setRenderData thread<br>
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

        super.paintComponent(g);

        // Hitbox visualization
        boolean renderHitBoxes = Objects.requireNonNull(ConfProvider.getConf(DataHand.confPath)).getProperty("game2d.setRenderData.hitboxes").equals("true");

        // setRenderData chunks that have objects in them
        boolean renderActiveChunks = Objects.requireNonNull(ConfProvider.getConf(DataHand.confPath)).getProperty("game2d.setRenderData.activechunks").equals("true");

        Graphics2D g2 = frame.createGraphics();

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        g2.setColor(Color.magenta);

        // draw each object that is in setRenderData distance
        ChunkMan.setRenderDataByChunk(g2, Camera.renderUpdate(), renderHitBoxes, renderActiveChunks);

        DebugScreen.draw(g2);

        //effect(img, 1);

        g2.dispose();

        g.drawImage(frame, 0, 0, this);

        frame.flush();
    }

    public void effect(BufferedImage img, int type){
        for(int i = 0; i < DataHand.renderLoop.getWidth(); i++){
            for(int j = 0; j < DataHand.renderLoop.getHeight(); j++){
                int pixel = img.getRGB(i, j);

                int red   = (pixel >> 16) & 0xFF;
                int green = (pixel >> 8)  & 0xFF;
                int blue  = (pixel)       & 0xFF;

                int newRGB = 0;
                switch(type){
                    case 0 -> {
                        //SETTING EVERYTHING TO GREYSCALE
                        int mid = (red + green + blue) / 3;
                        newRGB = ((mid & 0xFF) << 16) | ((mid & 0xFF) << 8) | (mid & 0xFF);
                    }
                    case 1 -> {
                        // INVERTING COLORS
                        red = 255 - red;
                        green = 255 - green;
                        blue = 255 - blue;
                        newRGB = ((red & 0xFF) << 16) | ((green & 0xFF) << 8) | (blue & 0xFF);
                    }
                    case 2 -> {
                        // SWITCHING CHANNELS
                        newRGB = ((green & 0xFF) << 16) | ((blue & 0xFF) << 8) | (red & 0xFF);
                    }
                    case 3 -> {
                        // NO RED
                        newRGB = ((green & 0xFF) << 8) | (blue & 0xFF);
                    }
                    case 4 -> {
                        // NO GREEN
                        newRGB = ((red & 0xFF) << 16) | (blue & 0xFF);
                    }
                    case 5 -> {
                        // NO BLUE
                        newRGB = ((red & 0xFF) << 16) | ((green & 0xFF) << 8);
                    }
                    case 6 -> {
                        // GANZE WERTE
                        red = red > 128 ? 255 : 0;
                        green = green > 128 ? 255 : 0;
                        blue = blue > 128 ? 255 : 0;
                        newRGB = ((red & 0xFF) << 16) | ((green & 0xFF) << 8) | (blue & 0xFF);
                    }
                    case 7 -> {
                        // VINTAGE
                        red = Math.min(255, (int)(0.393 * (double)red + 0.769 * (double)green + 0.189 * (double)blue));
                        green = Math.min(255, (int)(0.349 * (double)red + 0.686 * (double)green + 0.168 * (double)blue));
                        blue = Math.min(255, (int)(0.272 * (double)red + 0.534 * (double)green + 0.131 * (double)blue));
                        newRGB = ((red & 0xFF) << 16) | ((green & 0xFF) << 8) | (blue & 0xFF);
                    }
                }
                img.setRGB(i, j,  newRGB);
            }
        }
    }

    /**
     * Implement the rendering of GameObjects
     *
     * @param g the <code>Graphics</code> object to protect
     */
//    @Override
//    public void paintComponent(Graphics g) {
//
//        // Hitbox visualization
//        boolean renderHitBoxes = Objects.requireNonNull(ConfProvider.getConf(DataHand.confPath)).getProperty("game2d.render.hitboxes").equals("true");
//
//        // setRenderData chunks that have objects in them
//        boolean renderActiveChunks = Objects.requireNonNull(ConfProvider.getConf(DataHand.confPath)).getProperty("game2d.render.activechunks").equals("true");
//
//        super.paintComponent(g);
//
//        Graphics2D g2 = (Graphics2D) g;
//
//        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
//
//        g2.setColor(Color.magenta);
//
//        // draw each object that is in setRenderData distance
//        ChunkMan.setRenderDataByChunk(Camera.renderUpdate(), g2, renderHitBoxes, renderActiveChunks);
//
//        DebugScreen.draw(g2);
//
//        g2.dispose();
//    }

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
