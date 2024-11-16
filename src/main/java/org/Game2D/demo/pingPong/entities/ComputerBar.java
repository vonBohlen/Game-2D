package org.Game2D.demo.pingPong.entities;

import org.Game2D.demo.pingPong.PingPong;
import org.Game2D.engine.objects.advanced.Entity;

import java.awt.*;

public class ComputerBar extends Entity {

    public ComputerBar(Image txt) {
        super(new Rectangle( PingPong.screenWidth - 2 * (PingPong.screenWidth / 100), PingPong.screenHeight / 3, PingPong.screenWidth / 100, PingPong.screenHeight / 3), true, txt);
    }

    @Override
    public void update() {
    }

    @Override
    public void draw(Graphics2D g2) {
        g2.drawImage(texture, hitBox.x, hitBox.y, hitBox.width, hitBox.height, null);
    }

}
