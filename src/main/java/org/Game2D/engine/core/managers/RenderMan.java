package org.Game2D.engine.core.managers;

import org.Game2D.engine.chunks.Chunk;
import org.Game2D.engine.chunks.ChunkMan;
import org.Game2D.engine.core.handlers.DataHand;
import org.Game2D.engine.debug.DebugScreen;
import org.Game2D.engine.objects.GameObject;
import org.Game2D.engine.utils.ConfProvider;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class RenderMan extends JPanel implements Runnable {

    Thread renderThread;

    private boolean exit = false;
    private boolean run = true;

    private int posX = 0;
    private int posY = 0;

    public RenderMan() {

        confPanel();
        confPanel();

    }

    private void confPanel() {

        this.setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize());

        this.setBackground(Color.BLACK);

        this.setDoubleBuffered(true);

        this.addKeyListener(DataHand.keyHand);

        this.setFocusable(true);

    }

    public void startRenderLoop() {

        renderThread = new Thread(this);

        renderThread.start();

    }

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

    public void paintComponent(Graphics g) {

        // Hitbox visualization
        boolean renderHitBoxes = false;
        if (ConfProvider.getConf(DataHand.confPath).getProperty("game2d.render.hitboxes").equals("true")) {
            renderHitBoxes = true;
        }

        // render chunks that have objects in them
        boolean renderActiveChunks = false;
        try {
            if (ConfProvider.getConf(DataHand.confPath).getProperty("game2d.render.activechunks").equals("true")){
                renderActiveChunks = true;
            }
        }catch (Exception e){ /* config property doesnt exist yet */ }

        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        g2.setColor(Color.magenta);

        // draw each object that is in render distance
        ChunkMan.renderByChunk(posX + (getWidth() / 2), posY + (getHeight() / 2), g2, renderHitBoxes, renderActiveChunks);

        DebugScreen.draw(g2);

        g2.dispose();
    }

    public void freeze() {
        run = false;
    }

    public void resume() {
        run = true;
    }

    public void exit() {
        freeze();
        exit = true;
    }

}
