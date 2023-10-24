package de.Alienlive.games.Game2D.entities;

import de.Alienlive.games.Game2D.core.KeyHandler;
import de.Alienlive.games.Game2D.core.manager.ActionManager;
import de.Alienlive.games.Game2D.core.manager.RenderManager;
import de.Alienlive.games.Game2D.entities.entity.Entity;

import java.awt.*;

public class Player extends Entity {

    public Player(int pX, int pY, int pHeight, int pWidth, RenderManager pRender, ActionManager pAction, KeyHandler pKey) {
        super(pX, pY, pHeight, pWidth, pRender, pAction, pKey);
    }


    public void update() {
        if (keyHandler.keyPressed_W) {
            this.box.y -= 1;
        }
        if (keyHandler.keyPressed_S) {
            this.box.y += 1;
        }
        if (keyHandler.keyPressed_A) {
            this.box.x -= 1;
        }
        if (keyHandler.keyPressed_D) {
            this.box.x += 1;
        }
        ScreenSaver();
    }

    private int MoveX = 2;
    private int MoveY = 2;
    public void ScreenSaver(){
        if(this.box.getX() + MoveX <= 0){
            this.MoveX = 2;
        }
        else if(this.box.getX() + MoveX >= 1000){
            this.MoveX = -2;
        }

        if(this.box.getY() + MoveY <= 0){
            this.MoveY = 2;
        }
        if(this.box.getY() + MoveY >= 750){
            this.MoveY = -2;
        }

        this.box.x += MoveX;
        this.box.y += MoveY;
    }

    public void draw(Graphics2D g2) {

        g2.setColor(Color.WHITE);

        g2.fillRect(this.box.x, this.box.y, 48, 48);

    }


}
