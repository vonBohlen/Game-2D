package org.Game2D.engine.objects.advanced;

import org.Game2D.engine.objects.GameObject;
import org.Game2D.engine.physics.HitBox;

public abstract class StaticObject extends GameObject {

    public StaticObject(HitBox hitbox) {
        super(hitbox);
    }

}
