package de.Game2D.engine.debug;


import de.Game2D.engine.core.Instance;
import de.Game2D.engine.objects.advanced.Entity;


import java.awt.*;

public class DebugDisplay extends Entity {

    int fps;
    int tps;

    public DebugDisplay(Instance i) {
        super(i, null);
    }

    public void updateFPS(int fps) {
        this.fps = fps;
    }

    public void updateTPS(int tps) {
        this.tps = tps;
    }

    @Override
    public void update() {}

    @Override
    public void draw(Graphics2D g2) {
        if (/*Boolean.parseBoolean(PropertiesManager.getSettings().getProperty("game2d.core.showDebugScreen"))*/ true) {
            g2.setColor(Color.RED);

            g2.drawString("FPS: " + this.fps, 20, 20);

            g2.drawString("TPS: " + this.tps, 20, 30);
        }
    }

}
