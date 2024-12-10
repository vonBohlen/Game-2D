package org.Game2D.v1.demo.pingPong.entities;

import org.Game2D.v1.demo.pingPong.PingPong;
import org.Game2D.v1.engine.core.handlers.DataHand;
import org.Game2D.v1.engine.objects.advanced.Entity;

import java.awt.*;

public class Ball extends Entity {

    public Ball(boolean collision, Image txt) {
        super(new Rectangle(0,0, PingPong.screenWidth / 50, PingPong.screenWidth / 50), collision, txt);
    }

    @Override
    public void update() {
        if (hitBox.y >= DataHand.renderMan.getHeight()) setPosition(0, 0, false);
        if (DataHand.actionMan.getGameTick() != 30 && DataHand.actionMan.getGameTick() != 60) return;

        int x = hitBox.x + 50;
        int y = x / 10;

        move(50, y);

    }

    @Override
    public void draw(Graphics2D g2) {
        g2.setColor(Color.WHITE);
        g2.drawImage(texture, hitBox.x, hitBox.y, hitBox.width, hitBox.height, null);
    }
}
