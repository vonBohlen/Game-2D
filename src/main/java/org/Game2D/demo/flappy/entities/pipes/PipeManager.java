/**
 * @author The Game2D contributors
 */

package org.Game2D.demo.flappy.entities.pipes;

import org.Game2D.demo.flappy.entities.Bird;
import org.Game2D.engine.core.handlers.DataHand;
import org.Game2D.engine.objects.advanced.Entity;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PipeManager extends Entity {

    final int neededPipes;
    final int distanceTopBelow = 500;
    final int distancePipes = 300;

    int posX;
    int posY;
    int random;

    final List<PipeTop> tops = new ArrayList<>();
    final List<PipeBelow> belows = new ArrayList<>();

    public PipeManager(){
        super(new Rectangle(), false, -1, null);

        //first the amount of needed pipes is calculated
        //the distance between two pipes will be 300 pixels
        this.neededPipes = DataHand.renderMan.getWidth() / this.distancePipes + 1;

        //the first position for the pipes gets set
        this.posX = DataHand.renderMan.getWidth() / 2;
        this.posY = DataHand.renderMan.getHeight() / 2 + this.distanceTopBelow / 2;

        CreatePipes();
    }

    private void CreatePipes(){
        for(int i = 0; i < this.neededPipes; i++){

            this.random = getRnd();
            //112 because it's the base height and the 20 is so that there is a bit of distance between top/bottom and the pipe end
            if(this.posY + this.random < distancePipes + 20 || this.posY + this.random > DataHand.renderMan.getHeight() - 112 - 20){
                this.random *= -1;
            }

            tops.add(new PipeTop(posX + i*distancePipes, posY + this.random));
            //612 is used because the pipe is 612 pixels high
            belows.add(new PipeBelow(posX + i*distancePipes, posY + this.random - this.distanceTopBelow - 612));
            System.out.println("New pair created");

            this.posY += this.random;
        }
    }

    private int getRnd(){
        Random random = new Random();
        return random.nextInt(200 + 200 + 1) - 200;
    }

    @Override
    public void update() {
        if(!Bird.gameOver){
            movePipes();
        }
        else if (DataHand.keyHand.keyPressed_SPACE){
            setDefault();
        }
    }

    void movePipes(){
        this.random = getRnd();
        //112 because it's the base height and the 20 is so that there is a bit of distance between top/bottom and the pipe end
        if(this.posY + this.random < distancePipes + 20 || this.posY + this.random > DataHand.renderMan.getHeight() - 112 - 20){
            this.random *= -1;
        }

        for(PipeTop pipe : tops){
            if(pipe.hitBox.x < 0 - pipe.hitBox.getWidth()){

                int newX = pipe.hitBox.x + neededPipes * distancePipes;
                int newY = posY + this.random;
                pipe.setPosition(newX, newY);

                int index = tops.indexOf(pipe);
                belows.get(index).setPosition(newX, newY - distanceTopBelow - 612);
            }
        }
    }

    @Override
    public void render(Graphics2D g2) {}

    private void setDefaultAlt(){

        for(PipeTop top : tops){
            top.setPosition(top.hitBox.x, top.hitBox.y + 1000);
        }
        for(PipeBelow below : belows){
            below.setPosition(below.hitBox.x, below.hitBox.y + 1000);
        }

        this.posX = DataHand.renderMan.getWidth() / 2;
        this.posY = DataHand.renderMan.getHeight() / 2 + this.distanceTopBelow / 2;

        for(int i = 0; i < this.neededPipes; i++){

            this.random = getRnd();
            //112 because it's the base height and the 20 is so that there is a bit of distance between top/bottom and the pipe end
            if(this.posY + this.random < distancePipes + 20 || this.posY + this.random > DataHand.renderMan.getHeight() - 112 - 20){
                this.random *= -1;
            }

            tops.get(i).setPosition(posX + i*distancePipes, posY + this.random);
            belows.get(i).setPosition(posX + i*distancePipes, posY + this.random - this.distanceTopBelow - 612);

            this.posY += this.random;
        }

    }
    private void setDefault(){
        //Standard values
        this.posX = DataHand.renderMan.getWidth() / 2;
        this.posY = DataHand.renderMan.getHeight() / 2 + this.distanceTopBelow / 2;

        for(int i = 0; i < this.neededPipes; i++){

            this.random = getRnd();
            //112 because it's the base height and the 20 is so that there is a bit of distance between top/bottom and the pipe end
            if(this.posY + this.random < distancePipes + 20 || this.posY + this.random > DataHand.renderMan.getHeight() - 112 - 20){
                this.random *= -1;
            }

            tops.get(i).setPosition(posX + i*distancePipes, posY + this.random);
            //612 is used because the pipe is 612 pixels high
            belows.get(i).setPosition(posX + i*distancePipes, posY + this.random - this.distanceTopBelow - 612);
            this.posY += this.random;
        }
        Bird.gameOver = false;
    }
}
