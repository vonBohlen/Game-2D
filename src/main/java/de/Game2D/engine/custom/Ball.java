package de.Game2D.engine.custom;

import de.Game2D.engine.core.Instance;
import de.Game2D.engine.objects.Entity;

import java.awt.*;

public class Ball extends Entity {

    public Ball(int pX, int pY, int pHeight, int pWidth, Instance i) {
        super(i, new Rectangle(pX, pY, pWidth, pHeight));
    }

    private int MoveX = 6;
    private int MoveY = 6;
    private boolean lastMoveX = true;
    private boolean lastMoveY = true;

    public void update(){

        if(hitBox.x + MoveX <= 0 || hitBox.x + MoveX >= 1850 || !lastMoveX){
            this.MoveX = this.MoveX * -1;
        }

        if(hitBox.y + MoveY <= 0 || hitBox.y + MoveY >= 1100 || !lastMoveY) {
            this.MoveY = this.MoveY * -1;
        }

        lastMoveX = move(MoveX, 0);
        lastMoveY = move(0, MoveY);
    }

    public void draw(Graphics2D g2) {

        g2.setColor(Color.WHITE);

        g2.fillRect(hitBox.x, hitBox.y, 48, 48);

    }
}
