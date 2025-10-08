package org.Game2D.engine.core.managers;

import org.Game2D.engine.core.handlers.DataHand;
import org.Game2D.engine.objects.GameObject;
import org.Game2D.engine.utils.ConfProvider;
import org.Game2D.engine.debug.DebugScreen;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class RenderMan extends JPanel implements Runnable {

    Thread renderThread;

    private boolean exit = false;
    private boolean run = true;

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

            double drawInterval = 1000000000 / Integer.parseInt(ConfProvider.getConf(DataHand.confPath).getProperty("game2d.core.fps"));
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

        boolean renderHitBoxes = false;

        List<GameObject> gameObjects = DataHand.getGameObjs();


        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D)g;

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        g2.setColor(Color.magenta);

        if (ConfProvider.getConf(DataHand.confPath).getProperty("game2d.render.hitboxes").equals("true")) renderHitBoxes = true;

        for (GameObject go : gameObjects) {

            //if (ActionMan.getGameTick() == 0) System.out.println(go.getClass().getName() + " at " + go.objectLayer);

            if (go.getTexture() != null) go.render(g2);
            if (go.hitBox != null && renderHitBoxes) g2.draw3DRect(go.hitBox.x, go.hitBox.y, go.hitBox.width, go.hitBox.height, true);

        }

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
