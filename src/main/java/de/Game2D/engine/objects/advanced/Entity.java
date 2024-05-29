package de.Game2D.engine.objects.advanced;

import de.Game2D.engine.objects.GameObject;

import java.awt.*;

public abstract class Entity extends GameObject {

    public Entity(Rectangle hb, boolean collision, Image txt) {
        super(hb, collision, txt);
    }

    protected GameObject[] move(int xShift, int yShift) {

        if (hitBox == null) return null;

        GameObject objectCacheX, objectCacheY;

        GameObject[] objectCache = new GameObject[2];

        while (xShift != 0 || yShift != 0) {
            int newX = hitBox.x;
            int newY = hitBox.y;

            if (xShift < 0) {
                newX = hitBox.x - 1;
                xShift++;
            }
            else if (xShift > 0) {
                newX = hitBox.x + 1;
                xShift--;
            }

            if (yShift < 0) {
                newY = hitBox.y - 1;
                yShift++;
            }
            else if (yShift > 0) {
                newY = hitBox.y + 1;
                yShift--;
            }

            objectCacheX = null;
            objectCacheY = null;

            //objectCacheX = actionManager.checkCollision(this, new Rectangle(newX, hitBox.y, hitBox.width, hitBox.height));
            //objectCacheY = actionManager.checkCollision(this, new Rectangle(hitBox.x, newY, hitBox.width, hitBox.height));

            if (newX != hitBox.x) {
                if (objectCacheX.equals(null)) hitBox.x = newX;
                else {
                    xShift = 0;
                    objectCache[0] = objectCacheX;
                }
            }

            if (newY != hitBox.y) {
                if (objectCacheY.equals(null)) hitBox.y = newY;
                else {
                    yShift = 0;
                    objectCache[1] = objectCacheY;
                }
            }

        }
        if (objectCache[0].equals(null) && objectCache[1].equals(null)) return null;
        return objectCache;
    }

}
