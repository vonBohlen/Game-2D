package org.Game2D.demo.flappy.entities.pipes;

import org.Game2D.demo.flappy.FlappyBird;
import org.Game2D.demo.flappy.ScoreDisplay;
import org.Game2D.demo.flappy.entities.Bird;
import org.Game2D.engine.core.handlers.DataHand;
import org.Game2D.engine.objects.advanced.Entity;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Random;
import java.util.List;

public class PipeHandler extends Entity {

    public static int distancePipes = 500;

    int startX = DataHand.renderMan.getWidth() / 4 * 3;

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
        int twice = getRnd(-200, 200);

        int differenceA = adder > 0 ? adder : -adder;
        int differenceB = twice > 0 ? twice : -twice;

        adder = differenceA > differenceB ? adder : twice;

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
        random = DataHand.renderMan.getHeight() / 2 + 150;
        if(Bird.gameOver && DataHand.keyHand.keyPressed_SPACE){
            for(int i = pairs.size() - 1; i >= 0; i--){
                pairs.get(i).reset();
                System.out.println("Pipe reset " + pairs.get(i).startX);
            }
            ScoreDisplay.reset();
            Bird.gameOver = false;
        }
    }

    @Override
    public void draw(Graphics2D g2) {

    }
}
