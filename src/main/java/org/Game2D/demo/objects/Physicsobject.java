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
    double dragConst = 0.01;
    double boost = 0.1;

    double speedLooseAtCollisionPercent = 0.1;

    public Physicsobject(boolean collision, Image txt, int tps) {
        super(new Rectangle(100,100,48,48), collision, txt);

        this.passedTime = 1/ (double) tps;
    }

    @Override
    public void update() {

        //If W or S is pressed there are jumpboosts
        if(DataHand.keyHand.keyPressed_W){
            this.veloY -= this.boost;
        }
        if(DataHand.keyHand.keyPressed_S){
            this.veloY += this.boost;
        }


        //The movement on the x-axis is not accelerated
        //If A or D is pressed there is a speed boost
        if(DataHand.keyHand.keyPressed_A){
            this.veloX -= this.boost;
        }
        if(DataHand.keyHand.keyPressed_D){
            this.veloX += this.boost;
        }

        boolean acceleration = true;

        //Calculating what to use as Move Parameters
        int moveX = (int) this.veloX;
        int moveY = (int) this.veloY;

        //Wall logic -> if it hits wall it goes the opposite direction
        if(this.hitBox.x + moveX <= 0 || this.hitBox.x + moveX >= 1850){
            this.veloX = this.veloX*-1;
            moveX *= -1;

            //if the vriable for speed loose at collision is set the object looses a given percentage of its speed
            this.veloX *= 1-this.speedLooseAtCollisionPercent;
        }

        //Bottom and Top logic -> if it hits bottom or top it goes the opposite direction
        if(this.hitBox.y + moveY <= 0 || this.hitBox.y + moveY >= 1050){
            this.veloY = this.veloY*-1;
            moveY *= -1;
            acceleration = false;

            //if the vriable for speed loose at collision is set the object looses a given percentage of its speed
            this.veloY *= 1-this.speedLooseAtCollisionPercent;
        }


        //if a wall got hit the ball gets not accelerated
        if(acceleration) {
            //Calculating the velocity towards the ground
            // v = a * t
            this.veloY += this.gravityConst * this.passedTime;
        }

        //the new accelerated move variable
        moveY = (int) this.veloY;


        //the drag constant is just a number that actively works against the direction in which the object moves
        //if the subtraction of the drag constant would accelerate the object in the opposite direction the speed is set to 0
        //in X direction
        if((this.dragConst*-1) < veloX && this.dragConst > veloX){ veloX = 0;}
        if(veloX < 0){ veloX += this.dragConst; }
        if(veloX > 0){ veloX -= this.dragConst; }
        //in Y direction
        if((this.dragConst*-1) < veloY && this.dragConst > veloY){ veloY = 0;}
        if(veloY < 0){ veloY += this.dragConst; }
        if(veloY > 0){ veloY -= this.dragConst; }


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
