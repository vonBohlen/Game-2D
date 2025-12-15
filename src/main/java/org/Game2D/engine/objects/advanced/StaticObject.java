package org.Game2D.engine.objects.advanced;

import org.Game2D.engine.objects.GameObject;

import java.awt.*;

public abstract class StaticObject extends GameObject {

    public StaticObject(Rectangle hb, boolean collision, int objectLayer, Image txt) {
        super(hb, collision, objectLayer, true, txt);
    }

    @Override
    public void update() {}

}
