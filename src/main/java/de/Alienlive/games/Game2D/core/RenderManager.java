package de.Alienlive.games.Game2D.core;

import de.Alienlive.games.Game2D.core.Main;
import de.Alienlive.games.Game2D.core.PropertiesManager;
import de.Alienlive.games.Game2D.objects.entities.entity.Entity;

import javax.swing.*;
import java.awt.*;

public class RenderManager extends JPanel implements Runnable {
    
    Thread renderThread;

    public RenderManager() {
        this.setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize());
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(Main.getKeyHandler());
        this.setFocusable(true);
    }

    protected void startRenderThread() {
        renderThread = new Thread(this);
        renderThread.start();
    }

    @Override
    public void run() {

        double drawInterval = 1000000000 / Integer.parseInt(PropertiesManager.getSettings().getProperty("game2d.core.fps"));
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;

        while (renderThread != null) {

            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if (delta >= 1) {
                repaint();
                delta--;
                drawCount++;
            }

            if (timer >= 1000000000) {
                Main.getDebugDisplay().updateFPS(drawCount);
                drawCount = 0;
                timer = 0;
            }
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D)g;

        for (Entity ce: Main.getActionManager().getEntities()) {
            ce.draw(g2);
        }

        Main.getDebugDisplay().draw(g2);

        g2.dispose();
    }

}
