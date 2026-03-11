/**
 * /engine/objects/advanced/Entity.java
 *
 * The Entity class provides with entities associate features to a GameObject.
 *
 * Copyright (C) 2026 Christian von Bohlen, Silas Vogel, J. K.
 */

package org.Game2D.engine.objects.advanced;

import lombok.NonNull;
import org.Game2D.engine.chunks.managers.ObjectTransferMan;
import org.Game2D.engine.graphics.Texture;
import org.Game2D.engine.objects.GameObject;
import org.Game2D.engine.objects.loops.GameLoop;

import java.awt.*;

/**
 * The Entity class provides with entities associate features to a GameObject.
 */
public abstract class Entity extends GameObject {

    /**
     * Creates a GameObject with entity features.
     *
     * @param renderEnabled Flag for rendering
     * @param collisionEnabled Flag for collisionEnabled
     * @param hitbox Hitbox for the GameObject
     * @param layerID Additional hitbox data for the layer of the GameObject
     * @param texture Texture for the GameObject
     * @param textureID ID for the texture
     */
    public Entity(boolean renderEnabled, boolean collisionEnabled, @NonNull Rectangle hitbox, int layerID, @NonNull Texture texture, @NonNull String textureID) {

        super(renderEnabled, collisionEnabled, hitbox, layerID, texture, textureID);

    }

    /**
     * Creates a GameObject with entity features.
     *
     * @param renderEnabled Flag for rendering
     * @param collisionEnabled Flag for collision
     * @param hitbox Hitbox for the GameObject
     * @param layerID Additional hitbox data for the layer of the GameObject
     */
    public Entity(boolean renderEnabled, boolean collisionEnabled, @NonNull Rectangle hitbox, int layerID) {

        super(renderEnabled, collisionEnabled, hitbox, layerID);

    }

    /**
     * Creates a GameObject with entity features.
     *
     * @param collisionEnabled Flag for collision
     * @param hitbox Hitbox for the GameObject
     * @param layerID Additional hitbox data for the layer of the GameObject
     * @param texture Texture for the GameObject
     * @param textureID ID for the texture
     */
    public Entity(boolean collisionEnabled, @NonNull Rectangle hitbox, int layerID, @NonNull Texture texture, @NonNull String textureID) {

        super(true, collisionEnabled, hitbox, layerID, texture, textureID);

    }

    /**
     * Creates a GameObject with entity features.
     *
     * @param collisionEnabled Flag for collision
     * @param hitbox Hitbox for the GameObject
     * @param layerID Additional hitbox data for the layer of the GameObject
     */
    public Entity(boolean collisionEnabled, @NonNull Rectangle hitbox, int layerID) {

        super(true, collisionEnabled, hitbox, layerID);

    }

    /**
     * Moves the entity for the provided x- and y-shift.
     *
     * @param xShift Units to move x-coordinate
     * @param yShift Units to move y-coordinate
     * @return GameObjects the entity collided with during moving
     */
    protected GameObject[] move(int xShift, int yShift) {

        // TODO: Replace methode

        GameObject objectCacheX, objectCacheY;
        GameObject[] objectCache = new GameObject[2];

        int oldX = hitbox.x;
        int oldY = hitbox.y;

        while (xShift != 0 || yShift != 0) {
            int stepX = xShift != 0 ? Math.min(Math.abs(xShift), 5) * Integer.signum(xShift) : 0;
            int stepY = yShift != 0 ? Math.min(Math.abs(yShift), 5) * Integer.signum(yShift) : 0;

            int newX = hitbox.x + stepX;
            int newY = hitbox.y + stepY;

            objectCacheX = GameLoop.checkCollision(this, new Rectangle(newX, hitbox.y, hitbox.width, hitbox.height));
            objectCacheY = GameLoop.checkCollision(this, new Rectangle(hitbox.x, newY, hitbox.width, hitbox.height));

            if (stepX != 0) {
                if (objectCacheX == null) {
                    hitbox.x = newX;
                    xShift -= stepX;
                } else {
                    xShift = 0;
                    objectCache[0] = objectCacheX;
                }
            }

            if (stepY != 0) {
                if (objectCacheY == null) {
                    hitbox.y = newY;
                    yShift -= stepY;
                } else {
                    yShift = 0;
                    objectCache[1] = objectCacheY;
                }
            }

            if (objectCache[0] != null && objectCache[1] != null) break;
        }

        ObjectTransferMan.checkTransferAfterMoveAbs(this, oldX, oldY);

        return (objectCache[0] == null && objectCache[1] == null) ? null : objectCache;

    }

    /**
     * Changes the position of the entity by setting new x- and y-coordinate values.
     *
     * @param newX New x-coordinate
     * @param newY New y-coordinate
     * @param ignoreCollision Ignore collision while setting new coordinates
     */
    public void setPosition(int newX, int newY, boolean ignoreCollision) {

        int oldX = hitbox.x;
        int oldY = hitbox.y;

        Rectangle newPosition = new Rectangle(newX, newY, hitbox.width, hitbox.height);
        GameObject objectCache = GameLoop.checkCollision(this, newPosition);

        if (objectCache != null && !ignoreCollision) return;

        hitbox.x = newX;
        hitbox.y = newY;

        ObjectTransferMan.checkTransferAfterMoveAbs(this, oldX, oldY);

    }

    /**
     * Changes the position of the entity by setting new x- and y-coordinate values.
     *
     * @param newX New x-coordinate
     * @param newY New y-coordinate
     */
    public GameObject setPosition(int newX, int newY) {

        Rectangle newPosition = new Rectangle(newX, newY, hitbox.width, hitbox.height);
        GameObject objectCache = GameLoop.checkCollision(this, newPosition);

        if (objectCache != null) return objectCache;

        hitbox.x = newX;
        hitbox.y = newY;

        return null;

    }

    /**
     * Changes width and height of the entity.
     *
     * @param newWidth New entity width
     * @param newHeight New entity height
     * @return GameObjects the entity collided with during size change
     */
    public GameObject changeEntitySize(int newWidth, int newHeight) {

        Rectangle newSize = new Rectangle(hitbox.x, hitbox.y, newWidth, newHeight);
        GameObject objectCache = GameLoop.checkCollision(this, newSize);

        if (objectCache != null) return objectCache;

        hitbox.width = newWidth;
        hitbox.height = newHeight;

        return null;

    }

}
