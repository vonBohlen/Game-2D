package org.Game2D.v0.demo.general.entities;

import org.Game2D.v0.engine.core.handlers.DataHand;
import org.Game2D.v0.engine.objects.advanced.Entity;

import java.awt.*;
import java.util.Random;

public class TpSquare extends Entity {

    public TpSquare( Rectangle hb, Image txt) {
        super(hb, true, 0, txt);
    }

    public void update() {

        if (!(DataHand.actionMan.getGameTick() == 30)) return;

        Random random = new Random();
        int randomX, randomY;

        while (true) {
            randomX = random.nextInt(DataHand.renderMan.getWidth()-hitBox.width);
            randomY = random.nextInt(DataHand.renderMan.getHeight()-hitBox.height);

            if (setPosition(randomX, randomY) == null) break;
        }
    }

    public void render(Graphics2D g2) {

        g2.setColor(Color.WHITE);

        g2.drawImage(texture, hitBox.x, hitBox.y, hitBox.width, hitBox.height, null);

    }

}
