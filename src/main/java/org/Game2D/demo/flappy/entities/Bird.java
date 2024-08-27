package org.Game2D.demo.flappy.entities;

import org.Game2D.engine.core.handlers.DataHand;
import org.Game2D.engine.objects.advanced.Entity;

import java.awt.*;

public class Bird extends Entity {

    double velo = 0.0; //only one velocity because the bird stays in position on x-axis
    double veloOnPress = -15.0;

    double passedTime; //at 60 tps it is around 1.6

    double gravityConst = 9.81; //earths gravity is 9.81

    public Bird(Image txt) {
        //bird gets placed at one half of the height and one third of the width
        super(new Rectangle(48, 48, DataHand.renderMan.getWidth() / 3, DataHand.renderMan.getHeight() / 2), true, txt);
    }

    private void updatePosition() {

        //if space bar is pressed the birds velocity is set to a fixed value
        if(DataHand.keyHand.keyPressed_SPACE){
            this.velo = this.veloOnPress;
        }
    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Graphics2D g2) {

    }
}
