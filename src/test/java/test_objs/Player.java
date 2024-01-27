package test_objs;

import com.sun.tools.javac.Main;
import de.Game2D.engine.core.KeyHandler;
import de.Game2D.engine.objects.advanced.Entity;
import de.Game2D.engine.objects.ObjectConfig;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.*;
import java.util.Objects;

public class Player extends Entity {

    private final KeyHandler keyHandler;

    public Player(ObjectConfig config) {
        super(config);

        keyHandler = config.getInstance().getKeyHandler();

    }


    public void update() {

        int moveA = 0;
        int moveD = 0;

        int moveW = 0;
        int moveS = 0;

        if (keyHandler.keyPressed_W) {
            moveW = -7;
        }
        if (keyHandler.keyPressed_S) {
            moveS = 7;
        }
        if (keyHandler.keyPressed_A) {
            moveA = -7;
        }
        if (keyHandler.keyPressed_D) {
            moveD = 7;
        }

        move(moveA + moveD, moveW + moveS);
    }

    /*
    private int MoveX = 5;
    private int MoveY = 4;

    private boolean lastMoveX = true;
    private boolean lastMoveY = true;
    public void ScreenSaver(){
        if(hitBox.x + MoveX <= 0 || hitBox.x + MoveX >= 1850 || !lastMoveX){
            this.MoveX = this.MoveX * -1;
            if(this.MoveX <0){
                this.MoveX--;
            }
            else{
                this.MoveX++;
            }
        }

        if(hitBox.y + MoveY <= 0 || hitBox.y + MoveY >= 1100 || !lastMoveY) {
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
    */

    public void draw(Graphics2D g2) {

        g2.setColor(Color.WHITE);

        g2.drawImage(texture, hitBox.x, hitBox.y, hitBox.width, hitBox.height, null);

    }

}
