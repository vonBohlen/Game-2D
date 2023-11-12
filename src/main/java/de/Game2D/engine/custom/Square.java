package de.Game2D.engine.custom;

import de.Game2D.engine.core.Instance;
import de.Game2D.engine.old.entities.entity.Entity;

import java.awt.*;

public class Square extends Entity {

    public Square(int pX, int pY, int pHeight, int pWidth, Instance i) {
        super(pX, pY, pHeight, pWidth, i);
    }

    public void update() {}

    public void draw(Graphics2D g2) {

        g2.setColor(Color.WHITE);

        g2.fillRect(this.box.x, this.box.y, 48, 48);

    }

}
