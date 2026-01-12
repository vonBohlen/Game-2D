/**
 * @author The Game2D contributors
 */

package org.Game2D.tools;

import org.Game2D.engine.data.runtime.DataHand;
import org.Game2D.engine.io.conf.ConfProvider;
import org.Game2D.engine.io.user.Keyhand;

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

    private static String printPressedKeys() {
        String pressedKeys = "";
        Keyhand keyhand = DataHand.keyHand;
        if (keyhand.keyPressed_A) pressedKeys += " A";
        if (keyhand.keyPressed_D) pressedKeys += " D";
        if (keyhand.keyPressed_S) pressedKeys += " S";
        if (keyhand.keyPressed_W) pressedKeys += " W";
        if (keyhand.keyPressed_SPACE) pressedKeys += " SPACE";
        if (keyhand.keyPressed_ESC) pressedKeys += " ESC";
        return  pressedKeys;
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

            g2.setColor(Color.BLUE);

            g2.drawString("Keys_pressed:" + printPressedKeys(), 20, 95);
        }
    }

}
