package org.Game2D.demo.pingPong.entities;

import org.Game2D.demo.pingPong.PingPong;
import org.Game2D.engine.core.handlers.DataHand;
import org.Game2D.engine.objects.advanced.Entity;

import java.awt.*;

public class Ball extends Entity {

    public Ball(boolean collision, Image txt) {
        super(new Rectangle(0,0, PingPong.screenWidth / 50, PingPong.screenWidth / 50), collision, txt);
    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Graphics2D g2) {
        g2.setColor(Color.WHITE);
        g2.drawImage(texture, hitBox.x, hitBox.y, hitBox.width, hitBox.height, null);
    }
}
