package org.Game2D.demo.flappy.entities.pipes;

import org.Game2D.demo.flappy.ScoreDisplay;
import org.Game2D.demo.flappy.entities.Bird;
import org.Game2D.engine.core.handlers.DataHand;
import org.Game2D.engine.objects.advanced.Entity;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.List;

public class PipeHandler extends Entity {

    public static final int distancePipes = 500;

    final int startX = DataHand.renderMan.getWidth() / 4 * 3;

    public static final int pipesNum = DataHand.renderMan.getWidth() / distancePipes + 1;


    final List<PipePair> pairs = new ArrayList<>();


    //150 because default space between pipes is 300 (half so that its centered)
    static int random = DataHand.renderMan.getHeight() / 2 + 150;

    public PipeHandler(){
        super(new Rectangle(0,0), false, -1, null);
        initialisePipes();
    }

    void initialisePipes(){
        for(int i = 0; i < pipesNum; i++){
            pairs.add(new PipePair(startX + i*distancePipes));
        }
    }

    public static int getRndY(){
        int adder = getRnd();
        int twice = getRnd();

        int differenceA = adder > 0 ? adder : -adder;
        int differenceB = twice > 0 ? twice : -twice;

        adder = differenceA > differenceB ? adder : twice;

        if(adder + random < 300 || adder + random > DataHand.renderMan.getHeight()){
            adder *= -1;
        }
        random += adder;
        return random;
    }
    private static int getRnd(){
        Random random = new Random();
        return random.nextInt(200 + 200 + 1) - 200;
    }

    @Override
    public void update() {
        //only used for resetting the game
        random = DataHand.renderMan.getHeight() / 2 + 150;
        if(Bird.gameOver && DataHand.keyHand.keyPressed_SPACE){
            for(int i = pairs.size() - 1; i >= 0; i--){
                pairs.get(i).reset();
            }
            ScoreDisplay.reset();
            Bird.gameOver = false;
        }
    }

    @Override
    public void render(Graphics2D g2) {}
}
