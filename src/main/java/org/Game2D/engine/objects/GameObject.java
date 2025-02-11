package org.Game2D.engine.objects;

import org.Game2D.engine.physics.HitBox;

public abstract class GameObject {

    public final HitBox hitbox;

    public GameObject(HitBox hitbox) {
        this.hitbox = hitbox;
    }


}
