package org.Game2D.v1.demo.pingPong.entities;

import org.Game2D.v1.demo.pingPong.PingPong;
import org.Game2D.v1.engine.core.handlers.DataHand;
import org.Game2D.v1.engine.objects.advanced.Entity;

import java.awt.*;

public class PlayerBar extends Entity {

    public PlayerBar(Image txt) {
        super(new Rectangle(PingPong.screenWidth / 100, PingPong.screenHeight / 3, PingPong.screenWidth / 100, PingPong.screenHeight / 3), true, txt);
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
            setPosition(PingPong.screenWidth / 100, DataHand.renderMan.getHeight() - hitBox.height);
        }
        else if(hitBox.y < 0){
            setPosition(PingPong.screenWidth / 100, 0);
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        g2.drawImage(texture, hitBox.x, hitBox.y, hitBox.width, hitBox.height, null);
    }

}
