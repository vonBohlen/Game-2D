/**
 * @author The Game2D contributors
 */

package org.Game2D.engine.objects.advanced;

import org.Game2D.engine.chunks.manager.ObjectTransferMan;
import org.Game2D.engine.physics.loops.ActionLoop;
import org.Game2D.engine.objects.GameObject;

import java.awt.*;

public abstract class Entity extends GameObject {

    public Entity(Rectangle hitbox, boolean collision, int objectLayer, Image texture) {
        super(hitbox, collision, objectLayer, true, texture);
    }

    public Entity(Rectangle hitbox, boolean collision, int objectLayer) {
        super(hitbox, collision, objectLayer, true);
    }

//    protected GameObject[] move(int xShift, int yShift) {
//
//        if (hitBox == null) return null;
//
//        GameObject objectCacheX, objectCacheY;
//
//        GameObject[] objectCache = new GameObject[2];
//    }

//    protected GameObject[] move(int xShift, int yShift) {
//
//        if (hitBox == null) return null;
//
//        GameObject objectCacheX, objectCacheY;
//
//        GameObject[] objectCache = new GameObject[2];
//
//        while (xShift != 0 || yShift != 0) {
//            int newX = hitBox.x;
//            int newY = hitBox.y;
//
//            if (xShift < 0) {
//                newX = hitBox.x - 1;
//                xShift++;
//            }
//            else if (xShift > 0) {
//                newX = hitBox.x + 1;
//                xShift--;
//            }
//
//            if (yShift < 0) {
//                newY = hitBox.y - 1;
//                yShift++;
//            }
//            else if (yShift > 0) {
//                newY = hitBox.y + 1;
//                yShift--;
//            }
//
//            objectCacheX = ActionMan.checkCollision(this, new Rectangle(newX, hitBox.y, hitBox.width, hitBox.height));
//            objectCacheY = ActionMan.checkCollision(this, new Rectangle(hitBox.x, newY, hitBox.width, hitBox.height));
//
//            if (newX != hitBox.x) {
//                if (objectCacheX == null) hitBox.x = newX;
//                else {
//                    xShift = 0;
//                    objectCache[0] = objectCacheX;
//                }
//            }
//
//            if (newY != hitBox.y) {
//                if (objectCacheY == null) hitBox.y = newY;
//                else {
//                    yShift = 0;
//                    objectCache[1] = objectCacheY;
//                }
//            }
//
//        }
//
//        if (objectCache[0] == null && objectCache[1] == null) return null;
//
//        return objectCache;
//
//    }

    protected GameObject[] move(int xShift, int yShift) {

        if (hitBox == null) return null;

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


    public void setPosition(int newX, int newY, boolean ignoreCollision) {

        if (hitBox == null) return;

        int oldX = hitBox.x;
        int oldY = hitBox.y;

        Rectangle newPosition = new Rectangle(newX, newY, hitBox.width, hitBox.height);
        GameObject objectCache = ActionLoop.checkCollision(this, newPosition);

        if (objectCache != null && !ignoreCollision) return;

        hitBox.x = newX;
        hitBox.y = newY;

        ObjectTransferMan.checkTransferAfterMoveAbs(this, oldX, oldY);
    }

    public GameObject setPosition(int newX, int newY) {

        if (hitBox == null) return null;

        Rectangle newPosition = new Rectangle(newX, newY, hitBox.width, hitBox.height);
        GameObject objectCache = ActionLoop.checkCollision(this, newPosition);

        if (objectCache != null) return objectCache;

        hitBox.x = newX;
        hitBox.y = newY;

        return null;

    }


    public GameObject changeEntitySize(int newWidth, int newHeight) {

        if (hitBox == null) return null;

        Rectangle newSize = new Rectangle(hitBox.x, hitBox.y, newWidth, newHeight);
        GameObject objectCache = ActionLoop.checkCollision(this, newSize);

        if (objectCache != null) return objectCache;

        hitBox.width = newWidth;
        hitBox.height = newHeight;

        return null;

    }

}
