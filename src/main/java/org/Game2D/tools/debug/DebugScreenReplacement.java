package org.Game2D.tools.debug;

import org.Game2D.engine.chunks.managers.ChunkMan;
import org.Game2D.engine.data.runtime.DataHand;
import org.Game2D.engine.graphics.loops.RenderLoop;
import org.Game2D.engine.objects.loops.GameLoop;

import java.awt.*;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class DebugScreenReplacement {

    private static final ConcurrentHashMap<String, DebugParameter> debugParameters = new ConcurrentHashMap<>();

    public static void addDebugParameter(String name, DebugParameter parameter) {
        debugParameters.put(name, parameter);
    }

    public static void addDebugParameters(HashMap<String, DebugParameter> parameters) {
        debugParameters.putAll(parameters);
    }

    public static void updateDebugParameters(Graphics2D g2) {
        AtomicInteger y = new AtomicInteger(20);
        debugParameters.forEachKey(Integer.MAX_VALUE, name -> {
            g2.setColor(Color.white);
            String parameter = debugParameters.get(name).updateParameter(name, g2);
            g2.drawString(parameter, 20, y.get());
            y.addAndGet(15);
        });
    }

    public static void addDefaultDebugParameters() {
        addDebugParameter("FPS", ((name, g2) -> {
            g2.setColor(Color.RED);
            return name + "=" + RenderLoop.fps;
        }));
        addDebugParameter("Frame_time_ms",  ((name, g2) -> {
            g2.setColor(Color.RED);
            return name + "=" + RenderLoop.frameTime;
        }));
        addDebugParameter("TPS", ((name, g2) -> {
            g2.setColor(Color.RED);
            return name + "=" + GameLoop.tps;
        }));
        addDebugParameter("Tick_time_ms",  ((name, g2) -> {
            g2.setColor(Color.RED);
            return name + "=" + GameLoop.tickTime;
        }));
        addDebugParameter("Objects", ((name, g2) -> {
            g2.setColor(Color.YELLOW);
            return name + "=" + ChunkMan.getTotalObjectCount();
        }));
        addDebugParameter("Keys_pressed", ((name, g2) -> {
            String pressedKeys = "";
            if (DataHand.keyHand.keyPressed_A) pressedKeys += " A";
            if (DataHand.keyHand.keyPressed_D) pressedKeys += " D";
            if (DataHand.keyHand.keyPressed_S) pressedKeys += " S";
            if (DataHand.keyHand.keyPressed_W) pressedKeys += " W";
            if (DataHand.keyHand.keyPressed_SPACE) pressedKeys += " SPACE";
            if (DataHand.keyHand.keyPressed_ESC) pressedKeys += " ESC";
            pressedKeys = pressedKeys.replaceFirst(" ", "");
            g2.setColor(Color.BLUE);
            return name + "=" + pressedKeys;
        }));
    }

}
