package org.Game2D.demo.general.controlable;

import org.Game2D.engine.core.handlers.DataHand;
import org.Game2D.engine.objects.advanced.Entity;

import java.awt.*;

public class Player extends Entity {

    public Player(Rectangle hb, Image txt) {

        super(hb, true, 0, txt);

    }

    final int speed = 1;

    public void update() {

        int moveA = 0;
        int moveD = 0;

        int moveW = 0;
        int moveS = 0;

        if (DataHand.keyHand.keyPressed_W) {
            moveW = -speed;
        }
        if (DataHand.keyHand.keyPressed_S) {
            moveS = speed;
        }
        if (DataHand.keyHand.keyPressed_A) {
            moveA = -speed;
        }
        if (DataHand.keyHand.keyPressed_D) {
            moveD = speed;
        }

        move(moveA + moveD, moveW + moveS);
    }


    public void render(Graphics2D g2) {

        g2.setColor(Color.WHITE);

        g2.drawImage(texture, hitBox.x, hitBox.y, hitBox.width, hitBox.height, null);

    }

}
