package de.Alienlive.games.Game2D.entities.entity;

import de.Alienlive.games.Game2D.core.KeyHandler;
import de.Alienlive.games.Game2D.core.manager.ActionManager;
import de.Alienlive.games.Game2D.core.manager.RenderManager;

import java.awt.*;

public class Entity {

    //region Gamelogic
    public Rectangle box;
    public int speed;
    //endregion

    //region Handlers
    public RenderManager renderManager;
    public KeyHandler keyHandler;
    public ActionManager actionManager;
    //endregion

    public Entity(int pX, int pY, int pHeight, int pWidth, RenderManager pRender, ActionManager pAction, KeyHandler pKey){

        //region Handlers
        this.renderManager = pRender;
        this.actionManager = pAction;
        this.keyHandler = pKey;

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

    public void move(int xShift, int yShift) {
        Rectangle shift = new Rectangle(this.box.x + (xShift), this.box.y + (yShift), 48, 48);
        if (!actionManager.checkCollisionForEntity(shift,this)) {
            this.box.x += (xShift);
            this.box.y += (yShift);
        }
    }

    public void update() {}
    public void draw(Graphics2D g2) {}
}
