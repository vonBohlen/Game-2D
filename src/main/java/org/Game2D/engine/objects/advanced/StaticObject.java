/**
 * /engine/objects/advanced/StaticObject.java
 *
 * The StaticObject class provides a GameObject which cant be updated.
 *
 * Copyright (C) 2026 Christian von Bohlen, J. K.
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
     * @param layerID Additional hitbox data for the layer of the GameObject
     * @param texture Texture for the GameObject
     */
    public StaticObject(boolean renderEnabled, boolean collisionEnabled, @NonNull Rectangle hitbox, int layerID, @NonNull Image texture) {

        super(renderEnabled, collisionEnabled, hitbox, layerID, texture);

    }

    /**
     * Creates a GameObject which cant be updated.
     *
     * @param renderEnabled Flag for rendering
     * @param collisionEnabled Flag for collision
     * @param hitbox Hitbox for the GameObject
     * @param layerID Additional hitbox data for the layer of the GameObject
     */
    public StaticObject(boolean renderEnabled, boolean collisionEnabled, @NonNull Rectangle hitbox, int layerID) {

        super(renderEnabled, collisionEnabled, hitbox, layerID);

    }

    /**
     * Creates a GameObject which cant be updated.
     *
     * @param collisionEnabled Flag for collision
     * @param hitbox Hitbox for the GameObject
     * @param layerID Additional hitbox data for the layer of the GameObject
     * @param texture Texture for the GameObject
     */
    public StaticObject(boolean collisionEnabled, @NonNull Rectangle hitbox, int layerID, @NonNull Image texture) {

        super(true, collisionEnabled, hitbox, layerID, texture);

    }

    /**
     * Creates a GameObject which cant be updated.
     *
     * @param collisionEnabled Flag for collision
     * @param hitbox Hitbox for the GameObject
     * @param layerID Additional hitbox data for the layer of the GameObject
     */
    public StaticObject(boolean collisionEnabled, @NonNull Rectangle hitbox, int layerID) {

        super(true, collisionEnabled, hitbox, layerID);

    }

    // The update function of the GameObject gets overridden, to prevent it from being updated.
    @Override
    public void update() {}

}
