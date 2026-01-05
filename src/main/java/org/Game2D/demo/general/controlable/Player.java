/**
 * @author The Game2D contributors
 */

package org.Game2D.demo.general.controlable;

import org.Game2D.engine.chunks.Chunk;
import org.Game2D.engine.chunks.ChunkMan;
import org.Game2D.engine.core.handlers.DataHand;
import org.Game2D.engine.objects.advanced.Entity;

import java.awt.*;

public class Player extends Entity {

    public Player(Rectangle hb, Image txt) {

        super(hb, true, 0, txt);

    }

    final int speed = 10;

    public void update() {

        boolean moved = false;
        int moveA = 0;
        int moveD = 0;

        int moveW = 0;
        int moveS = 0;

        if (DataHand.keyHand.keyPressed_W) {
            moveW = -speed;
            moved = true;
        }
        if (DataHand.keyHand.keyPressed_S) {
            moveS = speed;
            moved = true;
        }
        if (DataHand.keyHand.keyPressed_A) {
            moveA = -speed;
            moved = true;
        }
        if (DataHand.keyHand.keyPressed_D) {
            moveD = speed;
            moved = true;
        }

        if(moved) move(moveA + moveD, moveW + moveS);
    }

}
