/**
 * @author The Game2D contributors
 */

package org.Game2D.demo.general.entities;

import org.Game2D.engine.data.runtime.DataHand;
import org.Game2D.engine.physics.loops.ActionLoop;
import org.Game2D.engine.objects.advanced.Entity;

import java.awt.*;
import java.util.Random;

public class TpSquare extends Entity {

    public TpSquare( Rectangle hb, Image txt) {
        super(hb, true, 0, txt);
    }

    public void update() {

        if (!(ActionLoop.getGameTick() == 30)) return;

        Random random = new Random();
        int randomX, randomY;

        do {
            randomX = random.nextInt(DataHand.renderLoop.getWidth() - hitBox.width);
            randomY = random.nextInt(DataHand.renderLoop.getHeight() - hitBox.height);

        } while (setPosition(randomX, randomY) != null);
    }
}
