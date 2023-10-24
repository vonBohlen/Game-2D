package de.Alienlive.games.Game2D.entities.entity;

import de.Alienlive.games.Game2D.core.manager.ActionManager;
import de.Alienlive.games.Game2D.core.manager.RenderManager;

import java.awt.*;

public class Entity {

    public Rectangle box;
    public int speed;

    public RenderManager renderManager;
    public KeyHandler keyHandler;
    public ActionManager actionManager;

    public Entity(int pX, int pY, int pHeight, int pWidth, RenderManager pRender, ActionManager pAction, KeyHandler pKey){

        //region Other Objects
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

        actionManager.registerEntity(this);

    }

    public void update() {}
    public void draw(Graphics2D g2) {}

    enum Direction{
        up,
        left,
        down,
        right
    }
    public void move(Direction direction, int units){
        switch(direction){
            case up ->{this.box.TopLeft.y -= units;}
            case left ->{this.box.TopLeft.x -= units;}
            case down ->{this.box.TopLeft.y += units;}
            case right ->{this.box.TopLeft.x += units;}
        }

        //todo Event OnMove
    }
}
