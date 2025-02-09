package org.Game2D.v0.engine.debug;

import org.Game2D.v0.engine.core.handlers.DataHand;
import org.Game2D.v0.engine.utils.ConfProvider;

import java.awt.*;
import java.util.Objects;

public class DebugScreen {

    private static int FPS = 0;
    private static long FRAMETIME = 0;

    private static int TPS = 0;
    private static long TICKTIME = 0;

    public static void updateFPS(int fps) {
        if (fps == FPS) return;
        FPS = fps;
    }

    public static void updateFrameTime(long frameTime) {
        if (frameTime == FRAMETIME) return;
        FRAMETIME = frameTime;
    }

    public static void updateTPS(int tps) {
        if (tps == TPS) return;
        TPS = tps;
    }

    public static void updateTickTime(long tickTime) {
        if (tickTime == TICKTIME) return;
        TICKTIME = tickTime;
    }

    public static void draw(Graphics2D g2) {
        if (Boolean.parseBoolean(Objects.requireNonNull(ConfProvider.getConf(DataHand.confPath)).getProperty("game2d.core.showDebugScreen"))) {
            g2.setColor(Color.RED);

            g2.drawString("FPS: " + FPS, 20, 20);
            g2.drawString("Time_ns: " + FRAMETIME, 20, 35);

            g2.drawString("TPS: " + TPS, 20, 50);
            g2.drawString("Time_ns: " + TICKTIME, 20, 65);

            g2.setColor(Color.YELLOW);

            g2.drawString("Objects: " + DataHand.getGameObjs().size(), 20, 80);
        }
    }

}
