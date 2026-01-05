package org.Game2D.engine.objects.advanced;

import org.Game2D.engine.objects.GameObject;

import java.awt.*;

public abstract class StaticObject extends GameObject {

    public StaticObject(Rectangle hitbox, boolean collision, int objectLayer, Image texture) {
        super(hitbox, collision, objectLayer, true, texture);
    }

    public StaticObject(Rectangle hitbox, boolean collision, int objectLayer) {
        super(hitbox, collision, objectLayer, true);
    }

    @Override
    public void update() {
    }

}
