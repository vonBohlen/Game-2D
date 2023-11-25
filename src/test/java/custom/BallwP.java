package custom;

import de.Game2D.engine.core.Instance;
import de.Game2D.engine.objects.Entity;

import java.awt.*;

public class BallwP extends Entity {

    public BallwP(int pX, int pY, int pHeight, int pWidth, Instance i) {
        super(i, new Rectangle(pX, pY, pWidth, pHeight));
    }

    private int MoveX = 6;

    private int MoveY = -10;

    int tickBottomReached = 0;

    private boolean lastMoveX = true;
    private boolean lastMoveY = true;

    public void update(){

        if (tickBottomReached != 0 && hitBox.y == 1050 && tickBottomReached == actionManager.getGameTick()) {
            hitBox.x = 0;
            hitBox.y = 0;
            tickBottomReached = 0;
        }

        if (tickBottomReached == 0 && hitBox.y == 1050) {
            tickBottomReached = actionManager.getGameTick();
        }

        if(hitBox.x + MoveX <= 0 || hitBox.x + MoveX >= 1850 || !lastMoveX){
            this.MoveX = this.MoveX * -1;
        }

        if(hitBox.y + MoveY <= 0 || hitBox.y + MoveY >= 1050 || !lastMoveY) {
            this.MoveY = this.MoveY * -1;
        }


        MoveY += 2;

        if(hitBox.x <= 0){
            setPosition(0, hitBox.y);
        }
        if(hitBox.y <= 0){
            setPosition(hitBox.x, 0);
        }

        if(hitBox.x >= 1850){
            setPosition(1850, hitBox.y);
        }
        if(hitBox.y >= 1050){
            setPosition(hitBox.x, 1050);
            //this.MoveY = -10;
        }

        lastMoveX = move(MoveX, 0);
        lastMoveY = move(0, MoveY);
    }

    public void draw(Graphics2D g2) {

        g2.setColor(Color.WHITE);

        g2.fillRect(hitBox.x, hitBox.y, 48, 48);

    }
}
