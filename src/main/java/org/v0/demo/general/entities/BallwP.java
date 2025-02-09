package org.Game2D.v0.demo.general.entities;

import org.Game2D.v0.engine.core.handlers.DataHand;
import org.Game2D.v0.engine.objects.advanced.Entity;

import java.awt.*;

public class BallwP extends Entity {

    public BallwP( Rectangle hb, Image txt) {
        super(hb, true, 0, txt);
    }

    private int MoveX = 0;

    private int MoveY = 0;

    int tickBottomReached = 0;

    public void update(){

        if (tickBottomReached != 0 && hitBox.y == 1050 && tickBottomReached == DataHand.actionMan.getGameTick()) {
            hitBox.x = 0;
            hitBox.y = 0;
            tickBottomReached = 0;
        }

        if (tickBottomReached == 0 && hitBox.y == 1050) {
            tickBottomReached = DataHand.actionMan.getGameTick();
        }

        //Wenn Wände getroffen an den Seiten bewegungsrichtung verändern
        if(hitBox.x + MoveX <= 0 || hitBox.x + MoveX >= 1850){
            this.MoveX = this.MoveX * -1;
        }

        //Wenn Boden oder Decke getroffen bewegungsrichtung verändern
        if(hitBox.y + MoveY <= 0 || hitBox.y + MoveY >= 1050) {
            this.MoveY = this.MoveY * -1;
        }


        MoveY += 2;

        //Teleportierlogik -> Wenn es aus den Rändern des Fensters ist wird es zurück rein teleportiert
        if(hitBox.x <= 0){
            setPosition(0, hitBox.y, false);
        }
        if(hitBox.y <= 0){
            setPosition(hitBox.x, 0, false);
        }

        if(hitBox.x >= 1850){
            setPosition(1850, hitBox.y, false);
        }
        if(hitBox.y >= 1050){
            setPosition(hitBox.x, 1050, false);
            //this.MoveY = -10;
        }

        move(MoveX, 0);
        move(0, MoveY);
    }

    public void render(Graphics2D g2) {

        g2.setColor(Color.WHITE);

        g2.drawImage(texture, hitBox.x, hitBox.y, hitBox.width, hitBox.height, null);

    }
}
