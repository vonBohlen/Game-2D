package de.Game2D.engine.objects;

import de.Game2D.engine.core.Instance;

import java.awt.*;
import java.util.ArrayDeque;
import java.util.Deque;

public abstract class Entity extends GameObject {

    private final Deque<GameObject> collisions = new ArrayDeque<>();

    public Entity(Instance i, Rectangle hitBox) {
        super(i, hitBox);
    }

    /**
     * Moves the entity from its current position for the provided x and y shift values.
     * @param xShift shift on the x-axis
     * @param yShift shift on the y-axis
     * @return true if the move was successful, else false
     */
    protected boolean move(int xShift, int yShift) {
        if (hitBox == null) return false;
        Rectangle shift = new Rectangle(hitBox.x, hitBox.y, hitBox.width, hitBox.height);
        GameObject objectCache;

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

            shift.x = newX;
            shift.y = newY;

            objectCache = actionManager.checkCollision(this, shift);

            if (objectCache != null) {
                collisions.push(objectCache);
                return false;
            }

            hitBox.x = newX;
            hitBox.y = newY;
        }
        return true;
    }

    /**
     *Changes the position of the entity, by setting new x and y values.
     * @param newX new x-position for the entity
     * @param newY new y-position fot the entity
     * @return true if the position change successful, else false
     */
    public boolean setPosition(int newX, int newY) {
        if (hitBox == null) return false;
        Rectangle newPosition = new Rectangle(newX, newY, hitBox.width, hitBox.height);
        GameObject objectCache = actionManager.checkCollision(this, newPosition);
        if (objectCache != null) {
            collisions.push(objectCache);
            return false;
        }

        hitBox.x = newX;
        hitBox.y = newY;
        return true;
    }

    /**
     *Changes the size of the entity, by setting new width height values.
     * @param newWidth new width value for the entity
     * @param newHeight new height value for the entity
     * @return true if the resize was successful, else false
     */
    public boolean changeEntitySize(int newWidth, int newHeight) {
        if (hitBox == null) return false;
        Rectangle newSize = new Rectangle(hitBox.x, hitBox.y, newWidth, newHeight);
        GameObject objectCache = actionManager.checkCollision(this, newSize);
        if (objectCache != null) {
            collisions.push(objectCache);
            return false;
        }

        hitBox.width = newWidth;
        hitBox.height = newHeight;
        return true;
    }

    public GameObject getCollision() {
        return collisions.pop();
    }

}
