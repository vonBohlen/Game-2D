/**
 * @author The Game2D contributors
 */

package org.Game2D.engine.objects.advanced;

import lombok.NonNull;
import org.Game2D.engine.chunks.manager.ObjectTransferMan;
import org.Game2D.engine.objects.GameObject;
import org.Game2D.engine.objects.loops.ActionLoop;

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
     * @param objectLayer Additional hitbox data for the layer of the GameObject
     * @param texture Texture for the GameObject
     */
    public Entity(boolean renderEnabled, boolean collisionEnabled, @NonNull Rectangle hitbox, int objectLayer, @NonNull Image texture) {

        super(renderEnabled, collisionEnabled, hitbox, objectLayer, texture);

    }

    /**
     * Creates a GameObject with entity features.
     *
     * @param renderEnabled Flag for rendering
     * @param collisionEnabled Flag for collision
     * @param hitbox Hitbox for the GameObject
     * @param objectLayer Additional hitbox data for the layer of the GameObject
     */
    public Entity(boolean renderEnabled, boolean collisionEnabled, @NonNull Rectangle hitbox, int objectLayer) {

        super(renderEnabled, collisionEnabled, hitbox, objectLayer);

    }

    /**
     * Creates a GameObject with entity features.
     *
     * @param collisionEnabled Flag for collision
     * @param hitbox Hitbox for the GameObject
     * @param objectLayer Additional hitbox data for the layer of the GameObject
     * @param texture Texture for the GameObject
     */
    public Entity(boolean collisionEnabled, @NonNull Rectangle hitbox, int objectLayer, @NonNull Image texture) {

        super(true, collisionEnabled, hitbox, objectLayer, texture);

    }

    /**
     * Creates a GameObject with entity features.
     *
     * @param collisionEnabled Flag for collision
     * @param hitbox Hitbox for the GameObject
     * @param objectLayer Additional hitbox data for the layer of the GameObject
     */
    public Entity(boolean collisionEnabled, @NonNull Rectangle hitbox, int objectLayer) {

        super(true, collisionEnabled, hitbox, objectLayer);

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

        int oldX = hitBox.x;
        int oldY = hitBox.y;

        while (xShift != 0 || yShift != 0) {
            int stepX = xShift != 0 ? Math.min(Math.abs(xShift), 5) * Integer.signum(xShift) : 0;
            int stepY = yShift != 0 ? Math.min(Math.abs(yShift), 5) * Integer.signum(yShift) : 0;

            int newX = hitBox.x + stepX;
            int newY = hitBox.y + stepY;

            objectCacheX = ActionLoop.checkCollision(this, new Rectangle(newX, hitBox.y, hitBox.width, hitBox.height));
            objectCacheY = ActionLoop.checkCollision(this, new Rectangle(hitBox.x, newY, hitBox.width, hitBox.height));

            if (stepX != 0) {
                if (objectCacheX == null) {
                    hitBox.x = newX;
                    xShift -= stepX;
                } else {
                    xShift = 0;
                    objectCache[0] = objectCacheX;
                }
            }

            if (stepY != 0) {
                if (objectCacheY == null) {
                    hitBox.y = newY;
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

        int oldX = hitBox.x;
        int oldY = hitBox.y;

        Rectangle newPosition = new Rectangle(newX, newY, hitBox.width, hitBox.height);
        GameObject objectCache = ActionLoop.checkCollision(this, newPosition);

        if (objectCache != null && !ignoreCollision) return;

        hitBox.x = newX;
        hitBox.y = newY;

        ObjectTransferMan.checkTransferAfterMoveAbs(this, oldX, oldY);

    }

    /**
     * Changes the position of the entity by setting new x- and y-coordinate values.
     *
     * @param newX New x-coordinate
     * @param newY New y-coordinate
     */
    public GameObject setPosition(int newX, int newY) {

        Rectangle newPosition = new Rectangle(newX, newY, hitBox.width, hitBox.height);
        GameObject objectCache = ActionLoop.checkCollision(this, newPosition);

        if (objectCache != null) return objectCache;

        hitBox.x = newX;
        hitBox.y = newY;

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

        Rectangle newSize = new Rectangle(hitBox.x, hitBox.y, newWidth, newHeight);
        GameObject objectCache = ActionLoop.checkCollision(this, newSize);

        if (objectCache != null) return objectCache;

        hitBox.width = newWidth;
        hitBox.height = newHeight;

        return null;

    }

}
