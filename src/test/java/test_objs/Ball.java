package test_objs;

import de.Game2D.engine.core.Instance;
import de.Game2D.engine.objects.ObjectConfig;
import de.Game2D.engine.objects.advanced.Entity;

import java.awt.*;

public class Ball extends Entity {

    public Ball(ObjectConfig config) {
        super(config);
    }

    /*private int MoveX = 6;
    private int MoveY = 6;
    private boolean lastMoveX = true;
    private boolean lastMoveY = true;*/

    @Override
    public void update(){

        if (hitBox.y >= instance.getHeight()) setPosition(0, 0);
        if (actionManager.getGameTick() != 30 && actionManager.getGameTick() != 60) return;

        int x = hitBox.x + 50;
        int y = x / 10;

        move(50, y);

        /*if(hitBox.x + MoveX <= 0 || hitBox.x + MoveX >= 1850 || !lastMoveX){
            this.MoveX = this.MoveX * -1;
        }

        if(hitBox.y + MoveY <= 0 || hitBox.y + MoveY >= 1100 || !lastMoveY) {
            this.MoveY = this.MoveY * -1;
        }

        lastMoveX = move(MoveX, 0);
        lastMoveY = move(0, MoveY);*/
    }

    @Override
    public void draw(Graphics2D g2) {

        g2.setColor(Color.WHITE);

        g2.fillRect(hitBox.x, hitBox.y, hitBox.width, hitBox.height);

    }
}
