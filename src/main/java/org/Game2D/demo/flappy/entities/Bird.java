package org.Game2D.demo.flappy.entities;

import org.Game2D.engine.core.handlers.DataHand;
import org.Game2D.engine.objects.advanced.Entity;

import java.awt.*;

public class Bird extends Entity {

    double velo = 0.0; //only one velocity because the bird stays in position on x-axis
    double veloOnPress = -7.5;
    double gravityConst = 9.81; //earths gravity is 9.81

    double passedTime; //at 60 tps it is around 1.6

    boolean gameOver = false;

    public Bird(Image txt, int tps) {
        //bird gets placed at one half of the height and one third of the width
        super(new Rectangle(DataHand.renderMan.getWidth() / 5, DataHand.renderMan.getHeight() / 2, 48, 48), true, txt);

        //die Zeit die idealerweise zwischen zwei ticks vergeht
        this.passedTime = 1 / (double) tps;
    }

    private void updatePosition() {

        //if space bar is pressed the birds velocity is set to a fixed value
        if(DataHand.keyHand.keyPressed_SPACE){
            this.velo = this.veloOnPress;
        }

        //checks if the top is reached there is no check for bottom needed because the floor is an object
        if(this.hitBox.y <= 0){
            this.gameOver = true;
        }

        //calculating the speed that is added after the passed time
        this.velo += this.gravityConst * this.passedTime;

        //converting the velocity into the needed datatype for the move method
        int moving = (int) this.velo;

        //if null is returned the bird moved without touching an object
        //the if branche is to determine whether or whether game over was true before
        if(!gameOver) {
            this.gameOver = move(0, moving) != null;
        }
    }

    @Override
    public void update() {
        if(!this.gameOver) {
            updatePosition();
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        g2.setColor(Color.WHITE);
        g2.drawImage(texture, hitBox.x, hitBox.y, hitBox.width, hitBox.height, null);
    }
}
