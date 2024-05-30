package de.Game2D.engine.objects.advanced;

import de.Game2D.engine.objects.GameObject;

import java.awt.*;

public abstract class StaticObject extends GameObject {

    public StaticObject(Rectangle hb, boolean collision, Image txt) {
        super(hb, collision, txt);
    }

    @Override
    public void update() {}

}
