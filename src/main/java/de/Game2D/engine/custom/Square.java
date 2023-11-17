package de.Game2D.engine.custom;

import de.Game2D.engine.core.Instance;
import de.Game2D.engine.objects.Entity;

import java.awt.*;

public class Square extends Entity {

    public Square(int pX, int pY, int pHeight, int pWidth, Instance i) {
        super(i, new Rectangle(pX, pY, pWidth, pHeight));
    }

    public void update() {}

    public void draw(Graphics2D g2) {

        g2.setColor(Color.WHITE);

        g2.fillRect(hitBox.x, hitBox.y, 48, 48);

    }

}
