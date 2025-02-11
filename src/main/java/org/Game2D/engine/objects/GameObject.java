package org.Game2D.engine.objects;

import org.Game2D.engine.physics.Hitbox;

public abstract class GameObject {

    public final Hitbox hitbox;

    public GameObject(Hitbox hitbox) {
        this.hitbox = hitbox;
    }


}
