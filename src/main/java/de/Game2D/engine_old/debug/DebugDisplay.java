package de.Game2D.engine_old.debug;


import de.Game2D.engine_old.objects.ObjectConfig;
import de.Game2D.engine_old.objects.advanced.StaticObject;


import java.awt.*;

public class DebugDisplay extends StaticObject {

    int fps;
    int tps;

    public DebugDisplay(ObjectConfig config) {
        super(config);
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
