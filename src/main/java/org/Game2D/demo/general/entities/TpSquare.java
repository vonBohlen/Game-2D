package org.Game2D.demo.general.entities;

import org.Game2D.engine.core.handlers.DataHand;
import org.Game2D.engine.core.managers.ActionMan;
import org.Game2D.engine.objects.advanced.Entity;

import java.awt.*;
import java.util.Random;

public class TpSquare extends Entity {

    public TpSquare( Rectangle hb, Image txt) {
        super(hb, true, 0, txt);
    }

    public void update() {

        if (!(ActionMan.getGameTick() == 30)) return;

        Random random = new Random();
        int randomX, randomY;

        do {
            randomX = random.nextInt(DataHand.renderMan.getWidth() - hitBox.width);
            randomY = random.nextInt(DataHand.renderMan.getHeight() - hitBox.height);

        } while (setPosition(randomX, randomY) != null);
    }
}
