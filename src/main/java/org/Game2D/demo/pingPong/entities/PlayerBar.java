package org.Game2D.demo.pingPong.entities;

import org.Game2D.demo.pingPong.PingPong;
import org.Game2D.engine.core.handlers.DataHand;
import org.Game2D.engine.objects.advanced.Entity;

import java.awt.*;

public class PlayerBar extends Entity {

    public PlayerBar(boolean collision, Image txt) {
        super(new Rectangle(10, PingPong.screenHeight / 3, PingPong.screenWidth / 100, PingPong.screenHeight / 3), collision, txt);
    }

    @Override
    public void update() {
        //movement
        if (DataHand.keyHand.keyPressed_W) {
            move(0, -10);
        } else if (DataHand.keyHand.keyPressed_S) {
            move(0, 10);
        }
        //boundaries
        if(hitBox.y + hitBox.height > DataHand.renderMan.getHeight()){
            setPosition(10, DataHand.renderMan.getHeight() - hitBox.height);
        }
        else if(hitBox.y < 0){
            setPosition(10, 0);
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        g2.drawImage(texture, hitBox.x, hitBox.y, hitBox.width, hitBox.height, null);
    }

}
