/**
 * @author The Game2D contributors
 */

package org.Game2D.demo.general.entities;

import org.Game2D.engine.data.runtime.DataHand;
import org.Game2D.engine.objects.advanced.Entity;

import java.awt.*;

public class Ball extends Entity {

    public Ball(Rectangle hb, Image txt) {
        super(true, hb, 0, txt);
    }

    @Override
    public void update(){

        if (hitBox.y >= DataHand.renderLoop.getHeight()) setPosition(0, 0, false);
        //if (ActionMan.getGameTick() != 30 && ActionMan.getGameTick() != 60) return;

        int x = hitBox.x + 1;
        int y = x / 2;

        move(50, y);

    }
}
