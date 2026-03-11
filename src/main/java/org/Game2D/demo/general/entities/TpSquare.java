/**
 * @author The Game2D contributors
 */

package org.Game2D.demo.general.entities;

import org.Game2D.engine.data.runtime.DataHand;
import org.Game2D.engine.graphics.Texture;
import org.Game2D.engine.objects.advanced.Entity;
import org.Game2D.engine.objects.loops.GameLoop;

import java.awt.*;
import java.util.Random;

public class TpSquare extends Entity {

    public TpSquare( Rectangle hb, Image txt) {
        super(true, hb, 0);
    }

    public void update() {

        if (!(GameLoop.getGameTick() == 30)) return;

        Random random = new Random();
        int randomX, randomY;

        do {
            randomX = random.nextInt(DataHand.renderLoop.getWidth() - hitbox.width);
            randomY = random.nextInt(DataHand.renderLoop.getHeight() - hitbox.height);

        } while (setPosition(randomX, randomY) != null);
    }
}
