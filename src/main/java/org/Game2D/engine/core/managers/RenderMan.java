package org.Game2D.engine.core.managers;

import org.Game2D.engine.core.handlers.DataHand;
import org.Game2D.engine.objects.GameObject;
import org.Game2D.engine.utils.ConfProvider;
import org.Game2D.engine.utils.DebugScreen;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class RenderMan extends JPanel implements Runnable {

    Thread renderThread;

    private boolean exit = false;
    private boolean run = true;

    //private final Queue<BufferedImage> frameBuffer = new LinkedList<>();

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

//    private void renderFrame() {
//
//        BufferedImage frame = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_ARGB);
//
//        Graphics2D g2 = frame.createGraphics();
//
//        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
//
//        List<GameObject> gameObjects = DataHand.getGameObjs();
//
//        for (GameObject go : gameObjects) {
//
//            if (go.getTexture() != null) go.draw(g2);
//
//        }
//
//        DebugScreen.draw(g2);
//
//        g2.dispose();
//
//        frameBuffer.add(frame);
//
//    }

//   public void paintComponent(Graphics g) {
//
//        super.paintComponent(g);
//
//        g.drawImage(frameBuffer.poll(), 0, 0, this);
//
//    }

//    private BufferedImage renderFrame() {
//
//        BufferedImage frame = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_ARGB);
//
//        Graphics2D g2 = frame.createGraphics();
//
//        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
//
//        List<GameObject> gameObjects = DataHand.getGameObjs();
//
//        for (GameObject go : gameObjects) {
//
//            if (go.getTexture() != null) go.draw(g2);
//
//        }
//
//        DebugScreen.draw(g2);
//
//        g2.dispose();
//
//        return frame;
//
//    }
//
//       public void paintComponent(Graphics g) {
//
//        super.paintComponent(g);
//
//        g.drawImage(renderFrame(), 0, 0, this);
//
//    }

    public void paintComponent(Graphics g) {
        List<GameObject> gameObjects = DataHand.getGameObjs();

        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D)g;

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        g2.setColor(Color.magenta);

        for (GameObject go : gameObjects) {

            if (go.getTexture() != null) go.draw(g2);
            if (go.hitBox != null && ConfProvider.getConf(DataHand.confPath).getProperty("game2d.render.hitboxes").equals("true")) g2.draw3DRect(go.hitBox.x, go.hitBox.y, go.hitBox.width, go.hitBox.height, false);

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
