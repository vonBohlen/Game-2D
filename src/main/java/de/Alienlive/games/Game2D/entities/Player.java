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
            move(-1,0);
        }
        if (keyHandler.keyPressed_S) {
            move(1,0);
        }
        if (keyHandler.keyPressed_A) {
            move(0,-1);
        }
        if (keyHandler.keyPressed_D) {
            move(0,1);
        }
        //ScreenSaver();
    }

    private int MoveX = 5;
    private int MoveY = 4;
    public void ScreenSaver(){
        if(this.box.getX() + MoveX <= 0 || this.box.getX() + MoveX >= 1850){
            this.MoveX = this.MoveX * -1;
            if(this.MoveX <0){
                this.MoveX--;
            }
            else{
                this.MoveX++;
            }
        }

        if(this.box.getY() + MoveY <= 0 || this.box.getY() + MoveY >= 1100) {
            this.MoveY = this.MoveY * -1;
            if(this.MoveY <0){
                this.MoveY--;
            }
            else{
                this.MoveY++;
            }
        }

        move(MoveX, MoveY);
    }

    public void draw(Graphics2D g2) {

        g2.setColor(Color.WHITE);

        g2.fillRect(this.box.x, this.box.y, 48, 48);

    }


}
