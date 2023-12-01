package de.Game2D.engine.objects.advanced;

import de.Game2D.engine.core.Instance;
import de.Game2D.engine.objects.GameObject;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public abstract class Entity extends GameObject {

    public Entity(Instance i, Rectangle hitBox, boolean collision) {
        super(i, hitBox, collision);
    }

    /**
     * Moves the entity from its current position for the provided x and y shift values.
     * @param xShift shift on the x-axis
     * @param yShift shift on the y-axis
     * @return true if the move was successful, else false
     */
    protected List<GameObject> move(int xShift, int yShift) {
        if (hitBox == null) return null;
        GameObject objectCacheX, objectCacheY;

        List<GameObject> retObj = new ArrayList<>();

        while (xShift != 0 || yShift != 0) {
            int newX = hitBox.x;
            int newY = hitBox.y;

            if (xShift < 0) {
                newX = hitBox.x - 1;
                xShift++;
            }
            else if (xShift > 0){
                newX = hitBox.x + 1;
                xShift--;
            }

            if (yShift < 0) {
                newY = hitBox.y - 1;
                yShift++;
            }
            else if (yShift > 0){
                newY = hitBox.y + 1;
                yShift--;
            }

            objectCacheX = actionManager.checkCollision(this, new Rectangle(newX, hitBox.y, hitBox.width, hitBox.height));
            objectCacheY = actionManager.checkCollision(this, new Rectangle(hitBox.x, newY, hitBox.width, hitBox.height));

            if (objectCacheX != null) {
                retObj.add(objectCacheX);
            }
            else {
                hitBox.x = newX;
            }

            if (objectCacheY != null) {
                retObj.add(objectCacheY);
            }
            else {
                hitBox.y = newY;
            }
        }
        return retObj;
    }

    /**
     *Changes the position of the entity, by setting new x and y values.
     * @param newX new x-position for the entity
     * @param newY new y-position fot the entity
     * @return true if the position change successful, else false
     */
    public GameObject setPosition(int newX, int newY) {
        if (hitBox == null) return null;
        Rectangle newPosition = new Rectangle(newX, newY, hitBox.width, hitBox.height);
        GameObject objectCache = actionManager.checkCollision(this, newPosition);
        if (objectCache != null) {
            return objectCache;
        }

        hitBox.x = newX;
        hitBox.y = newY;
        return null;
    }

    /**
     *Changes the size of the entity, by setting new width height values.
     * @param newWidth new width value for the entity
     * @param newHeight new height value for the entity
     * @return true if the resize was successful, else false
     */
    public GameObject changeEntitySize(int newWidth, int newHeight) {
        if (hitBox == null) return null;
        Rectangle newSize = new Rectangle(hitBox.x, hitBox.y, newWidth, newHeight);
        GameObject objectCache = actionManager.checkCollision(this, newSize);
        if (objectCache != null) {
            return objectCache;
        }

        hitBox.width = newWidth;
        hitBox.height = newHeight;
        return null;
    }

}
