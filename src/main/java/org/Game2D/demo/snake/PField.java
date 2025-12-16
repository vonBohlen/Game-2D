package org.Game2D.demo.snake;

import org.Game2D.engine.objects.advanced.StaticObject;

import java.awt.*;

/**
 * Field for the snake or food in the PlayingField<br>
 * Currently only used for texturing the background
 */
public class PField extends StaticObject {

    public PField(Rectangle hb, boolean collision, int objectLayer, Image txt) {
        super(hb, collision, objectLayer, txt);
        this.render_enabled = false;
    }

    @Override
    public void render(Graphics2D g2) {

    }

    /**
     * Mark the PField for rendering, not properly implemented at the moment
     */
    public void show() {
        this.render_enabled = true;
    }
}
