package org.Game2D.demo.flappy.entities.pipes;

import org.Game2D.demo.flappy.entities.Bird;
import org.Game2D.engine.core.handlers.DataHand;
import org.Game2D.engine.objects.advanced.Entity;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.List;

public class PipeHandler extends Entity {

    int spaceTopBelow = 500;
    public static int distancePipes = 300;

    int startX = DataHand.renderMan.getWidth() / 2;

    public static int pipesNum = DataHand.renderMan.getWidth() / distancePipes + 1;


    List<PipePair> pairs = new ArrayList<>();


    //150 because default space between pipes is 300 (half so that its centered)
    static int random = DataHand.renderMan.getHeight() / 2 + 150;

    public PipeHandler(){
        super(new Rectangle(0,0), false, null);
        initialisePipes();
    }

    void initialisePipes(){
        for(int i = 0; i < pipesNum; i++){
            pairs.add(new PipePair(startX + i*distancePipes));
        }
    }

    public static int getRndY(){
        int adder = getRnd(-200, 200);
        if(adder + random < 300 || adder + random > DataHand.renderMan.getHeight()){
            adder *= -1;
        }
        random += adder;
        return random;
    }
    private static int getRnd(int a, int b){
        Random random = new Random();
        return random.nextInt(b - a + 1) + a;
    }

    @Override
    public void update() {
        //only used for reseting the game
        if(Bird.gameOver && DataHand.keyHand.keyPressed_SPACE){
            for(PipePair pair : pairs){
                pair.reset();
            }
            Bird.gameOver = false;
        }
    }

    @Override
    public void draw(Graphics2D g2) {

    }
}
