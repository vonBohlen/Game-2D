package org.Game2D.demo.flappy.entities.pipes;

import org.Game2D.engine.objects.advanced.Entity;

import java.awt.*;

public class PipePair extends Entity {

    PipeTop top;
    PipeBelow below;

    int startX;

    int space = 300;

    //Standard coordinates
    public PipePair(int x){
        super(null, false, null);
        startX = x;
        int y = PipeHandler.getRndY();
        top = new PipeTop(x, y);
        //Pipe is 612 pixels tall
        below = new PipeBelow(x, y - space - 612);
    }

    public void reset(){
        int y = PipeHandler.getRndY();
        top.setPosition(startX, y, true);
        below.setPosition(startX, y - space - 612, true);
    }

    @Override
    public void update() {
        try {
            if (top.hitBox.x < 0 - top.hitBox.getWidth()) {
                teleport();
            }
        }
        catch(Exception e){
            System.out.println("Methode called before object initialization");
        }
    }

    private void teleport(){
        int newX = top.hitBox.x + PipeHandler.pipesNum * PipeHandler.distancePipes;
        int y = PipeHandler.getRndY();
        top.setPosition(newX, y, true);
        below.setPosition(newX, y - space - 612, true);
    }

    @Override
    public void draw(Graphics2D g2) {

    }
}
