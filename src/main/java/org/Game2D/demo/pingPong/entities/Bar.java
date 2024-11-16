package org.Game2D.demo.pingPong.entities;

import org.Game2D.demo.pingPong.PingPong;
import org.Game2D.engine.objects.advanced.Entity;

import java.awt.*;

public class Bar extends Entity {

    public Bar(boolean collision, Image txt) {
        super(new Rectangle(0, PingPong.screenHeight / 4, 10, PingPong.screenHeight / 2), collision, txt);
    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Graphics2D g2) {
        g2.drawImage(texture, hitBox.x, hitBox.y, hitBox.width, hitBox.height, null);
    }

}
