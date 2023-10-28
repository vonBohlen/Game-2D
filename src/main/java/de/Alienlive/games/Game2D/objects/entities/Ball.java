package de.Alienlive.games.Game2D.objects.entities;

import de.Alienlive.games.Game2D.core.Instance;
import de.Alienlive.games.Game2D.core.KeyHandler;
import de.Alienlive.games.Game2D.core.ActionManager;
import de.Alienlive.games.Game2D.core.RenderManager;
import de.Alienlive.games.Game2D.objects.entities.entity.Entity;

import java.awt.*;

public class Ball extends Entity {

    public Ball(int pX, int pY, int pHeight, int pWidth, Instance i) {
        super(pX, pY, pHeight, pWidth, i);
    }

    private int MoveX = 6;
    private int MoveY = 6;
    private boolean lastMoveX = true;
    private boolean lastMoveY = true;

    public void update(){

        if(this.box.getX() + MoveX <= 0 || this.box.getX() + MoveX >= 1850 || !lastMoveX){
            this.MoveX = this.MoveX * -1;
        }

        if(this.box.getY() + MoveY <= 0 || this.box.getY() + MoveY >= 1100 || !lastMoveY) {
            this.MoveY = this.MoveY * -1;
        }

        lastMoveX = move(MoveX, 0);
        lastMoveY = move(0, MoveY);
    }

    public void draw(Graphics2D g2) {

        g2.setColor(Color.WHITE);

        g2.fillRect(this.box.x, this.box.y, 48, 48);

    }
}
