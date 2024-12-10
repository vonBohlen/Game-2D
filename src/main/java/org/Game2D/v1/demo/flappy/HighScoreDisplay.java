package org.Game2D.v1.demo.flappy;

import org.Game2D.v1.demo.flappy.entities.ScoreDigit;
import org.Game2D.v1.engine.core.handlers.DataHand;

import java.awt.*;
import java.util.ArrayList;

public class HighScoreDisplay {

    private static int highScore = 0;

    private static ArrayList<ScoreDigit> scoreDigits = new ArrayList<>();

    public static void init() {
        addDigit();
    }

    private static void addDigit() {
        scoreDigits.add(new ScoreDigit(new Rectangle(DataHand.renderMan.getWidth() - 44, 10, 24, 36)));
    }

    private static void positionNewDigit() {
        addDigit();

        int posX = DataHand.renderMan.getWidth() - 44;
        for (int i = scoreDigits.size() -1; i >= 0;) {
            scoreDigits.get(i).setPositionX(posX);
            posX -= 24;
            i--;
        }
    }

    private static void upScore() {
        for (int i = scoreDigits.size() - 1; i >= 0;) {
            if (scoreDigits.get(i).getNumber() < 9) {
                scoreDigits.get(i).setNumber(scoreDigits.get(i).getNumber() + 1);
                break;
            }
            else if (i == 0) {
                scoreDigits.get(i).setNumber(1);
                positionNewDigit();
                break;
            }
            else {
                scoreDigits.get(i).setNumber(0);
            }
            i--;
        }
        highScore++;
    }

    public static void checkHighScore(int score) {
        if (score > highScore) upScore();
    }

}
