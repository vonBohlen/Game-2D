package de.Game2D.engine.custom;

import de.Game2D.engine.core.Instance;
import de.Game2D.engine.old.entities.entity.Entity;

import java.awt.*;
import java.util.Random;

public class TpSquare extends Entity{

    public TpSquare(int pX, int pY, int pHeight, int pWidth, Instance i) {
        super(pX, pY, pHeight, pWidth, i);
    }

    public void update() {

        if (!(actionManager.getGameTick() == 30)) return;

        Random random = new Random();
        int randomX;
        int randomY;

        while (true) {
            randomX = random.nextInt(instance.getWidth()-box.width);
            randomY = random.nextInt(instance.getHeight()-box.height);

            if (setPosition(randomX, randomY)) break;
        }
    }

    public void draw(Graphics2D g2) {

        g2.setColor(Color.WHITE);

        g2.fillRect(box.x, box.y, box.width, box.height);

    }

}
