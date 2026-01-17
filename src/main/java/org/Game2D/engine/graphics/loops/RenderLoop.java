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

    // Only for effects - lazy initialized
    private BufferedImage effectBuffer;
    private int lastEffectType = -1;

    Camera camera;

    // Optimized rendering hints for better performance
    private static final RenderingHints RENDERING_HINTS = new RenderingHints(null);
    static {
        RENDERING_HINTS.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        RENDERING_HINTS.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
        RENDERING_HINTS.put(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_SPEED);
        RENDERING_HINTS.put(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_DISABLE);
    }

    /**
     * Instantiate a new <code>RenderManager</code>
     */
    public RenderLoop() {
        confPanel();
    }

    public void initializeCamera() {
        camera = new Camera(0, 0, 0);
    }

    /**
     * Set up the screen configuration
     */
    private void confPanel() {
        this.setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize());
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true); // Use Swing's built-in double buffering
        this.addKeyListener(DataHand.keyHand);
        this.setFocusable(true);
    }

    /**
     * Create and start a new rendering thread
     */
    public void startRenderLoop() {
        renderThread = new Thread(this);
        renderThread.start();
    }

    /**
     * Main rendering loop executed by the rendering thread
     * Maintains a constant frame rate
     */
    @Override
    public void run() {
        if (renderThread == null) return;

        double drawInterval = (double) 1000000000 / Integer.parseInt(
                ConfProvider.getConf(DataHand.confPath).getProperty("game2d.core.fps"));
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

                repaint(); // Swing handles the buffering internally

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

    /**
     * Main rendering method called by Swing
     *
     * @param g the <code>Graphics</code> object for rendering
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Check if visual effects are enabled
        boolean useEffects = false;
        int effectType = 0;
        try {
            String effectConfig = ConfProvider.getConf(DataHand.confPath).getProperty("game2d.render.effects");
            if (effectConfig != null && !effectConfig.equals("0")) {
                useEffects = true;
                effectType = Integer.parseInt(effectConfig);
            }
        } catch (Exception e) {
            // Fallback: no effects
        }

        if (useEffects && effectType > 0) {
            renderWithEffects(g, effectType);
        } else {
            renderDirect(g);
        }
    }

    /**
     * Direct rendering without effects (standard mode)
     */
    private void renderDirect(Graphics g) {
        boolean renderHitBoxes = Objects.requireNonNull(ConfProvider.getConf(DataHand.confPath))
                .getProperty("game2d.setRenderData.hitboxes").equals("true");
        boolean renderActiveChunks = Objects.requireNonNull(ConfProvider.getConf(DataHand.confPath))
                .getProperty("game2d.setRenderData.activechunks").equals("true");

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHints(RENDERING_HINTS);

        // Viewport clipping for better performance
        g2.setClip(0, 0, getWidth(), getHeight());

        g2.setColor(Color.magenta);

        // Draw each object within rendering distance
        ChunkMan.setRenderDataByChunk(g2, Camera.renderUpdate(), renderHitBoxes, renderActiveChunks);

        DebugScreen.draw(g2);
    }

    /**
     * Rendering with visual effects (only when needed)
     */
    private void renderWithEffects(Graphics g, int effectType) {
        boolean renderHitBoxes = Objects.requireNonNull(ConfProvider.getConf(DataHand.confPath))
                .getProperty("game2d.setRenderData.hitboxes").equals("true");
        boolean renderActiveChunks = Objects.requireNonNull(ConfProvider.getConf(DataHand.confPath))
                .getProperty("game2d.setRenderData.activechunks").equals("true");

        // Create/update effect buffer only when needed
        if (effectBuffer == null ||
                effectBuffer.getWidth() != getWidth() ||
                effectBuffer.getHeight() != getHeight() ||
                lastEffectType != effectType) {

            // Create compatible image for better performance
            effectBuffer = getGraphicsConfiguration()
                    .createCompatibleImage(getWidth(), getHeight());
            lastEffectType = effectType;
        }

        // Render to effect buffer
        Graphics2D bufferG2 = effectBuffer.createGraphics();
        bufferG2.setRenderingHints(RENDERING_HINTS);
        bufferG2.setClip(0, 0, getWidth(), getHeight());
        bufferG2.setColor(Color.magenta);

        // Clear background
        bufferG2.setColor(Color.BLACK);
        bufferG2.fillRect(0, 0, getWidth(), getHeight());
        bufferG2.setColor(Color.magenta);

        // Draw each object within rendering distance
        ChunkMan.setRenderDataByChunk(bufferG2, Camera.renderUpdate(), renderHitBoxes, renderActiveChunks);

        DebugScreen.draw(bufferG2);
        bufferG2.dispose();

        // Apply effect if not type 0 (no effect)
        if (effectType > 0) {
            applyEffect(effectBuffer, effectType);
        }

        // Draw final result
        g.drawImage(effectBuffer, 0, 0, this);
    }

    /**
     * Optimized effect application using direct pixel access
     */
    private void applyEffect(BufferedImage img, int type) {
        int width = img.getWidth();
        int height = img.getHeight();

        // Only process if valid size
        if (width <= 0 || height <= 0) return;

        // Direct pixel access for better performance
        int[] pixels = new int[width * height];
        img.getRGB(0, 0, width, height, pixels, 0, width);

        // Apply effect to pixel array
        for (int i = 0; i < pixels.length; i++) {
            int pixel = pixels[i];

            int a = (pixel >> 24) & 0xFF;
            int r = (pixel >> 16) & 0xFF;
            int g = (pixel >> 8) & 0xFF;
            int b = pixel & 0xFF;

            int newRGB = 0;

            switch(type) {
                case 1 -> { // INVERTING COLORS
                    r = 255 - r;
                    g = 255 - g;
                    b = 255 - b;
                    newRGB = (a << 24) | (r << 16) | (g << 8) | b;
                }
                case 2 -> { // SWITCHING CHANNELS
                    newRGB = (a << 24) | (g << 16) | (b << 8) | r;
                }
                case 3 -> { // NO RED
                    newRGB = (a << 24) | (g << 8) | b;
                }
                case 4 -> { // NO GREEN
                    newRGB = (a << 24) | (r << 16) | b;
                }
                case 5 -> { // NO BLUE
                    newRGB = (a << 24) | (r << 16) | (g << 8);
                }
                case 6 -> { // BINARY THRESHOLD
                    r = r > 128 ? 255 : 0;
                    g = g > 128 ? 255 : 0;
                    b = b > 128 ? 255 : 0;
                    newRGB = (a << 24) | (r << 16) | (g << 8) | b;
                }
                case 7 -> { // VINTAGE / SEPIA
                    int newR = Math.min(255, (int)(0.393 * r + 0.769 * g + 0.189 * b));
                    int newG = Math.min(255, (int)(0.349 * r + 0.686 * g + 0.168 * b));
                    int newB = Math.min(255, (int)(0.272 * r + 0.534 * g + 0.131 * b));
                    newRGB = (a << 24) | (newR << 16) | (newG << 8) | newB;
                }
                default -> { // GREYSCALE (fallback)
                    int avg = (r + g + b) / 3;
                    newRGB = (a << 24) | (avg << 16) | (avg << 8) | avg;
                }
            }
            pixels[i] = newRGB;
        }

        img.setRGB(0, 0, width, height, pixels, 0, width);
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
     * Stop the rendering thread and perform clean exit
     */
    public void exit() {
        freeze();
        exit = true;

        // Release resources
        if (effectBuffer != null) {
            effectBuffer.flush();
            effectBuffer = null;
        }

        renderThread = null;
    }

    /**
     * Cleanup when component is removed
     */
    @Override
    public void removeNotify() {
        super.removeNotify();
        exit();
    }
}