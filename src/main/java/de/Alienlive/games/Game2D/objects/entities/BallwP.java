package de.Alienlive.games.Game2D.objects.entities;

import de.Alienlive.games.Game2D.core.Instance;
import de.Alienlive.games.Game2D.core.KeyHandler;
import de.Alienlive.games.Game2D.core.ActionManager;
import de.Alienlive.games.Game2D.core.RenderManager;
import de.Alienlive.games.Game2D.objects.entities.entity.Entity;

import java.awt.*;

public class BallwP extends Entity {

    public BallwP(int pX, int pY, int pHeight, int pWidth, Instance i) {
        super(pX, pY, pHeight, pWidth, i);
    }

    private int MoveX = 6;

    private int MoveY = -10;

    private boolean lastMoveX = true;
    private boolean lastMoveY = true;

    public void update(){

        if(this.box.getX() + MoveX <= 0 || this.box.getX() + MoveX >= 1850 || !lastMoveX){
            this.MoveX = this.MoveX * -1;
        }

        if(this.box.getY() + MoveY <= 0 || this.box.getY() + MoveY >= 1050 || !lastMoveY) {
            this.MoveY = this.MoveY * -1;
        }


        MoveY += 2;

        if(this.box.getX() <= 0){
            setPosition(0, this.box.y);
        }
        if(this.box.getY() <= 0){
            setPosition(this.box.x, 0);
        }

        if(this.box.getX() >= 1850){
            setPosition(1850, this.box.y);
        }
        if(this.box.getY() >= 1050){
            setPosition(this.box.x, 1050);
            //this.MoveY = -10;
        }

        lastMoveX = move(MoveX, 0);
        lastMoveY = move(0, MoveY);
    }

    public void draw(Graphics2D g2) {

        g2.setColor(Color.WHITE);

        g2.fillRect(this.box.x, this.box.y, 48, 48);

    }
}
