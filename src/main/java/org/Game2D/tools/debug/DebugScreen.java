package org.Game2D.tools.debug;

import org.Game2D.engine.chunks.managers.ChunkMan;
import org.Game2D.engine.data.runtime.DataHand;
import org.Game2D.engine.graphics.loops.RenderLoop;
import org.Game2D.engine.objects.loops.GameLoop;
import org.Game2D.tools.debug.parameters.ColorDebugParameter;
import org.Game2D.tools.debug.parameters.DebugParameter;

import java.awt.*;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class DebugScreen {

    private static final int START_X = 20;
    private static final int START_Y = 20;
    private static final int LINE_HEIGHT = 15;

    private static final CopyOnWriteArrayList<String> parameterOrder = new CopyOnWriteArrayList<>();
    private static final ConcurrentHashMap<String, DebugParameter> debugParameters = new ConcurrentHashMap<>();

    public static void addDebugParameter(String name, DebugParameter parameter) {
        if (name == null || parameter == null) throw new IllegalArgumentException("Name and parameter cant be null");
        debugParameters.put(name, parameter);
        parameterOrder.add(name);
    }

    public static void addDebugParameters(Map<String, DebugParameter> parameters) {
        if (parameters != null) {
            parameters.forEach(DebugScreen::addDebugParameter);
        }
    }

    public static void addDefaultDebugParameters() {
        addDebugParameter("FPS", new ColorDebugParameter(Color.red,
                (name, g2) -> {return name + "=" + RenderLoop.fps;}
        ));

        addDebugParameter("Frame_time_ms", new ColorDebugParameter(Color.red,
                (name, g2) -> {return name + "=" + RenderLoop.frameTime;}
        ));

        addDebugParameter("TPS", new ColorDebugParameter(Color.red,
                (name, g2) -> {return name + "=" + GameLoop.tps;}
        ));

        addDebugParameter("Tick_time_ms", new ColorDebugParameter(Color.red,
                (name, g2) -> {return name + "=" + GameLoop.tickTime;}
        ));

        addDebugParameter("Objects", new ColorDebugParameter(Color.yellow,
                (name, g2) -> {return name + "=" + ChunkMan.getTotalObjectCount();}
        ));

        addDebugParameter("Keys_pressed", new ColorDebugParameter(Color.BLUE,
                (name, g2) -> {return name + "=" + DataHand.keyHand.getPressedKeysAsString();}
        ));
    }

    public static void updateParameterColor(String name, Color color) {
        DebugParameter parameter = debugParameters.get(name);
        if (parameter instanceof ColorDebugParameter colorDebugParameter) {
            colorDebugParameter.updateColor(color);
        }
    }

    public static void updateDebugParameters(Graphics2D g2) {
        if (g2 == null || debugParameters.isEmpty()) return;
        int y = START_Y;
        for (String name : parameterOrder) {
            DebugParameter parameter = debugParameters.get(name);
            g2.setColor(Color.white);
            String result = parameter.updateParameter(name, g2);
            g2.drawString(result, START_X, y);
            y += LINE_HEIGHT;
        }
    }



}
