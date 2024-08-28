package org.Game2D.demo.flappy.entities.pipes;

import org.Game2D.engine.core.handlers.DataHand;
import org.Game2D.engine.core.managers.RenderMan;
import org.Game2D.engine.objects.advanced.Entity;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class PipeManager extends Entity {

    int neededPipes;
    int distanceTopBelow = 500;
    int distancePipes = 300;

    int posX;
    int posY;
    int random;

    List<PipeTop> tops = new ArrayList<>();
    List<PipeBelow> belows = new ArrayList<>();

    Image txtTop;
    Image txtBelow;

    public PipeManager(){
        super(new Rectangle(), false, null);

        //first the amount of needed pipes is calculated
        //the distance between two pipes will be 300 pixels
        this.neededPipes = DataHand.renderMan.getWidth() / this.distancePipes + 1;

        //the first position for the pipes gets set
        this.posX = DataHand.renderMan.getWidth() / 2;
        this.posY = DataHand.renderMan.getHeight() / 2 + this.distanceTopBelow / 2;

        try {
            txtTop = ImageIO.read(Objects.requireNonNull(RenderMan.class.getClassLoader().getResource("flappy_assets/pipe/pipe-green.png")));
            txtBelow = ImageIO.read(Objects.requireNonNull(RenderMan.class.getClassLoader().getResource("flappy_assets/pipe/pipe-green-upside-down.png")));
        }
        catch (IOException e){
            throw new RuntimeException();
        }

        CreatePipes();
    }

    private void CreatePipes(){
        for(int i = 0; i < this.neededPipes; i++){

            this.random = getRnd(-200, 200);
            //112 because it's the base height and the 20 is so that there is a bit distance between top/bottom and the pipeend
            if(this.posY + this.random < distancePipes + 20 || this.posY + this.random > DataHand.renderMan.getHeight() - 112 - 20){
                this.random *= -1;
            }

            tops.add(new PipeTop(posX + i*distancePipes, posY + this.random, this.txtTop));
            //612 is used because the pipe is 612 pixels high
            belows.add(new PipeBelow(posX + i*distancePipes, posY + this.random - this.distanceTopBelow - 612, this.txtBelow));
            this.posY += this.random;
        }
    }

    private int getRnd(int a, int b){
        Random random = new Random();
        return random.nextInt(b - a + 1) + a;
    }

    @Override
    public void update() {

        this.random = getRnd(-200, 200);
        //112 because it's the base height and the 20 is so that there is a bit distance between top/bottom and the pipeend
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
    public void draw(Graphics2D g2) {

    }
}
