package de.Alienlive.games.Game2D.objects.entities;

import de.Alienlive.games.Game2D.core.Instance;
import de.Alienlive.games.Game2D.core.KeyHandler;
import de.Alienlive.games.Game2D.core.ActionManager;
import de.Alienlive.games.Game2D.core.RenderManager;
import de.Alienlive.games.Game2D.objects.entities.entity.Entity;

import java.awt.*;

public class Player extends Entity {

    public Player(int pX, int pY, int pHeight, int pWidth, Instance i) {
        super(pX, pY, pHeight, pWidth, i);
    }


    public void update() {

        int moveA = 0;
        int moveD = 0;

        int moveW = 0;
        int moveS = 0;

        if (keyHandler.keyPressed_W) {
            moveW = -5;
        }
        if (keyHandler.keyPressed_S) {
            moveS = 5;
        }
        if (keyHandler.keyPressed_A) {
            moveA = -5;
        }
        if (keyHandler.keyPressed_D) {
            moveD = 5;
        }

        move(moveA + moveD, moveW + moveS);
    }

    private int MoveX = 5;
    private int MoveY = 4;

    private boolean lastMoveX = true;
    private boolean lastMoveY = true;
    public void ScreenSaver(){
        if(this.box.getX() + MoveX <= 0 || this.box.getX() + MoveX >= 1850 || !lastMoveX){
            this.MoveX = this.MoveX * -1;
            if(this.MoveX <0){
                this.MoveX--;
            }
            else{
                this.MoveX++;
            }
        }

        if(this.box.getY() + MoveY <= 0 || this.box.getY() + MoveY >= 1100 || !lastMoveY) {
            this.MoveY = this.MoveY * -1;
            if(this.MoveY <0){
                this.MoveY--;
            }
            else{
                this.MoveY++;
            }
        }

        lastMoveX = move(MoveX, 0);
        lastMoveY = move(0, MoveY);
    }



    public void draw(Graphics2D g2) {

        g2.setColor(Color.WHITE);

        g2.fillRect(this.box.x, this.box.y, 48, 48);

    }


}
