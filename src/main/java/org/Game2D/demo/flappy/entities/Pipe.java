package org.Game2D.demo.flappy.entities;

import org.Game2D.engine.objects.advanced.Entity;

import java.awt.*;

public class Pipe extends Entity {

    public Pipe(int x, int y, boolean collision, Image txt) {
        super(new Rectangle(x, y, 52, 314), collision, txt);
    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Graphics2D g2) {
        g2.drawImage(texture, hitBox.x, hitBox.y, hitBox.width, hitBox.height, null);
    }
}
