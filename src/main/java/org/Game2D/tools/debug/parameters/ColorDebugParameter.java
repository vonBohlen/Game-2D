package org.Game2D.tools.debug.parameters;

import java.awt.*;

public class ColorDebugParameter implements DebugParameter {

    private Color color;
    private final DebugParameter parameter;

    public ColorDebugParameter(Color color, DebugParameter parameter) {
        this.color = color;
        this.parameter = parameter;
    }

    public void updateColor(Color color) {
        this.color = color;
    }

    @Override
    public String updateParameter(String name, Graphics2D g2) {
        g2.setColor(color);
        return parameter.updateParameter(name, g2);
    }
}
