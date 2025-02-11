package org.Game2D.engine.objects;

import org.Game2D.engine.physics.HitBox;
import org.Game2D.engine.rendering.Texture;

import java.util.ArrayList;
import java.util.List;

public abstract class GameObject {

    public final HitBox hitbox;

    public final List<Texture> textures = new ArrayList<>();

    public GameObject(HitBox hitbox) {
        this.hitbox = hitbox;
    }

    //To avoid write / read the same object
    public List<Texture> getTextures() {
        return new ArrayList<>(textures);
    }

    //To create animations
    public abstract void animationUpdate();

}
