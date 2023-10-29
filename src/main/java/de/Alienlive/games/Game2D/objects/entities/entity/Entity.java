package de.Alienlive.games.Game2D.objects.entities.entity;

import de.Alienlive.games.Game2D.core.Instance;
import de.Alienlive.games.Game2D.core.KeyHandler;
import de.Alienlive.games.Game2D.core.ActionManager;
import de.Alienlive.games.Game2D.core.RenderManager;

import java.awt.*;
import java.util.ArrayDeque;
import java.util.Deque;

public class Entity {

    //region GameLogic
    public Rectangle box;
    private Deque<Entity> entityCollisions = new ArrayDeque<>();
    //endregion

    //region Handlers
    public RenderManager renderManager;
    public KeyHandler keyHandler;
    public ActionManager actionManager;
    //endregion

    public Entity(int pX, int pY, int pHeight, int pWidth, Instance i){

        //region Handlers
        this.renderManager = i.getRenderManager();
        this.actionManager = i.getActionManager();
        this.keyHandler = i.getKeyHandler();

        actionManager.registerEntity(this);
        //endregion

        //region HitBoxCreation
        this.box = new Rectangle(pX, pY, pWidth, pHeight);
        //endregion
    }

    public Entity(RenderManager pRender, ActionManager pAction) {

        this.renderManager = pRender;
        this.actionManager = pAction;

        //actionManager.registerEntity(this);

    }

    public boolean move(int xShift, int yShift) {

        Rectangle shift = new Rectangle(this.box.x, this.box.y, this.box.width, this.box.height);
        Entity entityCache;

        while (xShift != 0 && yShift != 0) {
            int newX = 0;
            int newY = 0;

            if (xShift < 0) {
                newX = box.x - 1;
                xShift++;
            }
            else if (xShift > 0){
                newX = box.x + 1;
                xShift--;
            }

            if (yShift < 0) {
                newY = box.y - 1;
                yShift++;
            }
            else if (yShift > 0){
                newY = box.y + 1;
                yShift--;
            }

            shift.x = newX;
            shift.y = newY;

            entityCache = actionManager.checkCollisionForEntity(this, shift);

            if (entityCache != null) {
                entityCollisions.push(entityCache);
                return false;
            }

            this.box.x = newX;
            this.box.y = newY;
        }
        return true;

        /*boolean directionX = true;
        boolean directionY = true;

        if (xShift < 0) {
            directionX = false;
            xShift *= (-1);
        }
        if (yShift < 0) {
            directionY = false;
            yShift *= (-1);
        }

        Rectangle shift = new Rectangle(this.box.x, this.box.y, this.box.width, this.box.height);
        Entity entityCache;

        while (xShift != 0 && yShift != 0) {
            int newX = 0;
            int newY = 0;

            if (!directionX && xShift != 0) newX = box.x - 1;
            else if (xShift != 0) newX = box.x + 1;

            if (!directionY && yShift != 0) newY = box.y - 1;
            else if (yShift != 0) newY = box.y + 1;

            shift.x = newX;
            shift.y = newY;

            entityCache = actionManager.checkCollisionForEntity(this, shift);

            if (entityCache != null) {
                entityCollisions.push(entityCache);
                return false;
            }

            this.box.x = newX;
            this.box.y = newY;

            if (xShift != 0) xShift--;
            if (yShift != 0) yShift--;

            System.out.println("test");
        }

        return true;

        /*Rectangle shift = new Rectangle(this.box.x + (xShift), this.box.y + (yShift), this.box.width, this.box.height);
        Entity entityCache = actionManager.checkCollisionForEntity(this, shift);
        if (entityCache == null) {
            this.box.x += (xShift);
            this.box.y += (yShift);
            return true;
        }
        entityCollisions.push(entityCache);
        return false;*/
    }

    public boolean setPosition(int newX, int newY) {
        Rectangle newPosition = new Rectangle(newX, newY, this.box.width, this.box.height);
        Entity entityCache = actionManager.checkCollisionForEntity(this, newPosition);
        if (entityCache == null) {
            this.box.x = newX;
            this.box.y = newY;
            return true;
        }
        entityCollisions.push(entityCache);
        return false;
    }

    public boolean changeEntitySize(int newWidth, int newHeight) {
        Rectangle newSize = new Rectangle(this.box.x, this.box.y, newWidth, newHeight);
        Entity entityCache = actionManager.checkCollisionForEntity(this, newSize);
        if (entityCache == null) {
            this.box.width = newWidth;
            this.box.height = newHeight;
            return true;
        }
        entityCollisions.push(entityCache);
        return false;
    }

    public Entity getEntityCollision() {
        return entityCollisions.pop();
    }

    public void update() {}
    public void draw(Graphics2D g2) {}
}
