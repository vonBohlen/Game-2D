package org.Game2D.demo.flappy.entities.pipes;

import org.Game2D.demo.flappy.entities.Bird;
import org.Game2D.engine.objects.advanced.Entity;

import java.awt.*;

public class PipeTop extends Entity {

    int speed = 10;

    public PipeTop(int x, int y, Image txt) {
        super(new Rectangle(x, y, 104, 612), true, txt);
    }

    @Override
    public void update() {

        if(Bird.gameOver) return;

        move(-speed, 0);
    }

    @Override
    public void draw(Graphics2D g2) {
        g2.drawImage(texture, hitBox.x, hitBox.y, hitBox.width, hitBox.height, null);
    }
}
