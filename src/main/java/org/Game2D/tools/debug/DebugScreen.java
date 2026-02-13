/**
 * /tools/debug/DebugScreen.java
 *
 * DESCRIPTION
 *
 * Copyright (C) 2026 Christian von Bohlen
 */

package org.Game2D.tools.debug;

import org.Game2D.engine.chunks.managers.ChunkMan;
import org.Game2D.engine.data.runtime.DataHand;
import org.Game2D.engine.data.disk.conf.ConfProvider;
import org.Game2D.engine.io.user.KeyHand;

import java.awt.*;

public class DebugScreen {

    private static int FPS = 0;
    private static long FRAME_TIME = 0;

    private static int TPS = 0;
    private static long TICK_TIME = 0;

    public static boolean HARDWARE_ACCELERATION = false;

    private  static KeyHand keyhand = null;

    public static void updateFPS(int fps) {
        if (fps == FPS) return;
        FPS = fps;
    }

    public static void updateFrameTime(long frameTime) {
        if (frameTime == FRAME_TIME) return;
        FRAME_TIME = frameTime;
    }

    public static void updateTPS(int tps) {
        if (tps == TPS) return;
        TPS = tps;
    }

    public static void updateTickTime(long tickTime) {
        if (tickTime == TICK_TIME) return;
        TICK_TIME = tickTime;
    }

    private static String printPressedKeys() {
        if (keyhand == null) keyhand = DataHand.keyHand;
        String pressedKeys = "";
        if (keyhand.keyPressed_A) pressedKeys += " A";
        if (keyhand.keyPressed_D) pressedKeys += " D";
        if (keyhand.keyPressed_S) pressedKeys += " S";
        if (keyhand.keyPressed_W) pressedKeys += " W";
        if (keyhand.keyPressed_SPACE) pressedKeys += " SPACE";
        if (keyhand.keyPressed_ESC) pressedKeys += " ESC";
        pressedKeys = pressedKeys.replaceFirst(" ", "");
        return  pressedKeys;
    }

    public static void draw(Graphics2D g2) {
        if (ConfProvider.getConfValueAsBool("game2d.debug.tools.render_debug_screen")) {
            g2.setColor(Color.RED);

            g2.drawString(String.format("TARGET_FPS=%s", FPS), 20, 20);
            g2.drawString(String.format("Frame_time_ns=%s", FRAME_TIME), 20, 35);

            g2.drawString(String.format("TARGET_TPS=%s", TPS), 20, 50);
            g2.drawString(String.format("Tick_time_ns=%s", TICK_TIME), 20, 65);

            g2.setColor(Color.YELLOW);

            g2.drawString(String.format("Objects=%s", ChunkMan.getTotalObjectCount()), 20, 80);

            g2.setColor(Color.BLUE);

            g2.drawString(String.format("Keys_pressed=%S", printPressedKeys()), 20, 95);

            g2.drawString(String.format("Hardware_acceleration=%b", HARDWARE_ACCELERATION) , 20, 110);
        }
    }

}
