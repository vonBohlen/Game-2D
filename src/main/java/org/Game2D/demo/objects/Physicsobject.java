package org.Game2D.demo.objects;

import org.Game2D.engine.core.handlers.DataHand;
import org.Game2D.engine.core.managers.ActionMan;
import org.Game2D.engine.objects.advanced.Entity;

import java.awt.*;

public class Physicsobject extends Entity {

    double veloX = 0;
    double veloY = 0;

    double passedTime;

    double gravityConst = 9.81;

    public Physicsobject(boolean collision, Image txt, int tps) {
        super(new Rectangle(100,100,48,48), collision, txt);

        this.passedTime = 1/ (double) tps;
    }

    @Override
    public void update() {

        //If W or S is pressed there are jumpboosts
        if(DataHand.keyHand.keyPressed_W){
            this.veloY -= 0.1;
        }
        if(DataHand.keyHand.keyPressed_S){
            this.veloY += 0.1;
        }


        //The movement on the x-axis is not accelerated
        //If A or D is pressed there is a speed boost
        if(DataHand.keyHand.keyPressed_A){
            this.veloX -= 0.1;
        }
        if(DataHand.keyHand.keyPressed_D){
            this.veloX += 0.1;
        }

        boolean acceleration = true;

        //Calculating what to use as Move Parameters
        int moveX = (int) this.veloX;
        int moveY = (int) this.veloY;

        //Wall logic -> if it hits wall it goes the opposite direction
        if(this.hitBox.x + moveX <= 0 || this.hitBox.x + moveX >= 1850){
            this.veloX = this.veloX*-1;
            moveX *= -1;
        }

        //Bottom and Top logic -> if it hits bottom or top it goes the opposite direction
        if(this.hitBox.y + moveY <= 0 || this.hitBox.y + moveY >= 1050){
            this.veloY = this.veloY*-1;
            moveY *= -1;
            acceleration = false;
        }


        //if a wall got hit the ball gets not accelerated
        if(acceleration) {
            //Calculating the velocity towards the ground
            // v = a * t
            this.veloY += this.gravityConst * this.passedTime;
        }

        //the new accelerated move variable
        moveY = (int) this.veloY;

        //moving the object at last
        move(moveX, 0);
        move(0, moveY);
    }

    @Override
    public void draw(Graphics2D g2) {

        g2.setColor(Color.WHITE);

        g2.drawImage(texture, hitBox.x, hitBox.y, hitBox.width, hitBox.height, null);
    }
}
