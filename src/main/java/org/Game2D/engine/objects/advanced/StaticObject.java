/**
 * @author The Game2D contributors
 */

package org.Game2D.engine.objects.advanced;

import lombok.NonNull;
import org.Game2D.engine.objects.GameObject;

import java.awt.*;

/**
 * The StaticObject class provides a GameObject which cant be updated.
 */
public abstract class StaticObject extends GameObject {

    /**
     * Creates a GameObject which cant be updated.
     *
     * @param renderEnabled Flag for rendering
     * @param collisionEnabled Flag for collision
     * @param hitbox Hitbox for the GameObject
     * @param objectLayer Additional hitbox data for the layer of the GameObject
     * @param texture Texture for the GameObject
     */
    public StaticObject(boolean renderEnabled, boolean collisionEnabled, @NonNull Rectangle hitbox, int objectLayer, @NonNull Image texture) {

        super(renderEnabled, collisionEnabled, hitbox, objectLayer, texture);

    }

    /**
     * Creates a GameObject which cant be updated.
     *
     * @param renderEnabled Flag for rendering
     * @param collisionEnabled Flag for collision
     * @param hitbox Hitbox for the GameObject
     * @param objectLayer Additional hitbox data for the layer of the GameObject
     */
    public StaticObject(boolean renderEnabled, boolean collisionEnabled, @NonNull Rectangle hitbox, int objectLayer) {

        super(renderEnabled, collisionEnabled, hitbox, objectLayer);

    }

    /**
     * Creates a GameObject which cant be updated.
     *
     * @param collisionEnabled Flag for collision
     * @param hitbox Hitbox for the GameObject
     * @param objectLayer Additional hitbox data for the layer of the GameObject
     * @param texture Texture for the GameObject
     */
    public StaticObject(boolean collisionEnabled, @NonNull Rectangle hitbox, int objectLayer, @NonNull Image texture) {

        super(true, collisionEnabled, hitbox, objectLayer, texture);

    }

    /**
     * Creates a GameObject which cant be updated.
     *
     * @param collisionEnabled Flag for collision
     * @param hitbox Hitbox for the GameObject
     * @param objectLayer Additional hitbox data for the layer of the GameObject
     */
    public StaticObject(boolean collisionEnabled, @NonNull Rectangle hitbox, int objectLayer) {

        super(true, collisionEnabled, hitbox, objectLayer);

    }

    // The update function of the GameObject gets overridden, to prevent it from being updated.
    @Override
    public void update() {}

}
