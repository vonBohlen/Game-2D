package de.Alienlive.games.Game2D.debug;

import de.Alienlive.games.Game2D.core.ActionManager;
import de.Alienlive.games.Game2D.core.RenderManager;
import de.Alienlive.games.Game2D.objects.entities.entity.Entity;

import java.awt.*;

public class DebugDisplay extends Entity {

    int fps;
    int tps;

    public DebugDisplay(RenderManager pRender, ActionManager pAction) {
        super(pRender, pAction);
    }

    public void updateFPS(int fps) {
        this.fps = fps;
    }

    public void updateTPS(int tps) {
        this.tps = tps;
    }

    public void draw(Graphics2D g2) {
        if (/*Boolean.parseBoolean(PropertiesManager.getSettings().getProperty("game2d.core.showDebugScreen"))*/ true) {
            g2.setColor(Color.RED);

            g2.drawString("FPS: " + this.fps, 20, 20);

            g2.drawString("TPS: " + this.tps, 20, 30);
        }
    }


}
