package de.Alienlive.games.Game2D.objects.entities.entity;

import de.Alienlive.games.Game2D.core.Instance;
import de.Alienlive.games.Game2D.core.KeyHandler;
import de.Alienlive.games.Game2D.core.ActionManager;
import de.Alienlive.games.Game2D.core.RenderManager;

import java.awt.*;

public class Entity {

    //region GameLogic
    public Rectangle box;
    public int speed;
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
        Rectangle shift = new Rectangle(this.box.x + (xShift), this.box.y + (yShift), this.box.width, this.box.height);
        if (!actionManager.checkCollisionForEntity(this, shift)) {
            this.box.x += (xShift);
            this.box.y += (yShift);
            return true;
        }
        return false;
    }

    public boolean setPosition(int newX, int newY) {
        Rectangle newPosition = new Rectangle(newX, newY, this.box.width, this.box.height);
        if (!actionManager.checkCollisionForEntity(this, newPosition)) {
            this.box.x = newX;
            this.box.y = newY;
            return true;
        }
        return false;
    }

    public boolean changeEntitySize(int newWidth, int newHeight) {
        Rectangle newSize = new Rectangle(this.box.x, this.box.y, newWidth, newHeight);
        if (!actionManager.checkCollisionForEntity(this, newSize)) {
            this.box.width = newWidth;
            this.box.height = newHeight;
            return true;
        }
        return false;
    }

    public void update() {}
    public void draw(Graphics2D g2) {}
}
