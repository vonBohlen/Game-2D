package org.Game2D.engine.core.managers;

import org.Game2D.engine.core.handlers.DataHand;
import org.Game2D.engine.objects.GameObject;
import org.Game2D.engine.utils.ConfProvider;
import org.Game2D.engine.utils.DebugScreen;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class RenderMan extends JPanel implements Runnable {

    Thread renderThread;

    public RenderMan() {

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

        double drawInterval = 1000000000 / Integer.parseInt(ConfProvider.getConf(DataHand.confPath).getProperty("game2d.core.fps"));
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;
        long startTime;
        long frameTime;

        while (renderThread != null) {

            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if (delta >= 1) {

                startTime = System.nanoTime();

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

    public void paintComponent(Graphics g) {

        List<GameObject> gameObjects = DataHand.getGameObjs();

        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D)g;

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        for (GameObject go : gameObjects) {

            if (go.getTexture() != null) go.draw(g2);

        }

        DebugScreen.draw(g2);

        g2.dispose();
    }

}
