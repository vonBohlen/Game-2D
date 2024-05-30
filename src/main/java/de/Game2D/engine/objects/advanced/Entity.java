package de.Game2D.engine.objects.advanced;

import de.Game2D.engine.core.handlers.DataHand;
import de.Game2D.engine.objects.GameObject;

import java.awt.*;

public abstract class Entity extends GameObject {

    public Entity(Rectangle hb, boolean collision, Image txt) {

        super(hb, collision, txt);

        DataHand.regGameObj(this);

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

            objectCacheX = DataHand.actionMan.checkCollision(this, new Rectangle(newX, hitBox.y, hitBox.width, hitBox.height));
            objectCacheY = DataHand.actionMan.checkCollision(this, new Rectangle(hitBox.x, newY, hitBox.width, hitBox.height));

            if (newX != hitBox.x) {
                if (objectCacheX == null) hitBox.x = newX;
                else {
                    xShift = 0;
                    objectCache[0] = objectCacheX;
                }
            }

            if (newY != hitBox.y) {
                if (objectCacheY == null) hitBox.y = newY;
                else {
                    yShift = 0;
                    objectCache[1] = objectCacheY;
                }
            }

        }

        if (objectCache[0] == null && objectCache[1] == null) return null;

        return objectCache;

    }

    public GameObject setPosition(int newX, int newY) {

        if (hitBox == null) return null;

        Rectangle newPosition = new Rectangle(newX, newY, hitBox.width, hitBox.height);
        GameObject objectCache = DataHand.actionMan.checkCollision(this, newPosition);

        if (objectCache != null) return objectCache;

        hitBox.x = newX;
        hitBox.y = newY;

        return null;

    }

    public GameObject changeEntitySize(int newWidth, int newHeight) {

        if (hitBox == null) return null;

        Rectangle newSize = new Rectangle(hitBox.x, hitBox.y, newWidth, newHeight);
        GameObject objectCache = DataHand.actionMan.checkCollision(this, newSize);

        if (objectCache != null) return objectCache;

        hitBox.width = newWidth;
        hitBox.height = newHeight;

        return null;

    }

}
