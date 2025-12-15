package org.Game2D.demo.Snake;

import org.Game2D.engine.objects.advanced.StaticObject;

import java.awt.*;

public class PField extends StaticObject {

    public PField(Rectangle hb, boolean collision, int objectLayer, Image txt) {
        super(hb, collision, objectLayer, txt);
        this.render_enabled = false;
    }

    @Override
    public void render(Graphics2D g2) {

    }

    public void show() {
        this.render_enabled = true;
    }
}
