/**
 * @author The Game2D contributors
 */

package org.Game2D.demo.flappy;

import org.Game2D.demo.flappy.entities.ScoreDigit;
import org.Game2D.engine.core.handlers.DataHand;
import org.Game2D.engine.utils.AssetMan;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ScoreDisplay {

    private static int score = 0;


    public static final List<Image> numbers = AssetMan.loadAssets(new String[]{
            "flappy_assets/numbers/1.png",
            "flappy_assets/numbers/2.png",
            "flappy_assets/numbers/3.png",
            "flappy_assets/numbers/4.png",
            "flappy_assets/numbers/5.png",
            "flappy_assets/numbers/6.png",
            "flappy_assets/numbers/7.png",
            "flappy_assets/numbers/8.png",
            "flappy_assets/numbers/9.png",
            "flappy_assets/numbers/0.png"
    });

    private static final ArrayList<ScoreDigit> scoreDigits = new ArrayList<>();

    public static void init() {
        reset();
        //addDigit();
        HighScoreDisplay.init();
    }

    private static void addDigit() {
        scoreDigits.add(new ScoreDigit(new Rectangle(DataHand.renderMan.getWidth() / 2 - 12, 10, 24, 36)));
    }

    private static void centerDigit() {
        addDigit();

        if (scoreDigits.size() % 2 != 0) {
            int half = scoreDigits.size() / 2;
            int posX = DataHand.renderMan.getWidth() / 2 - 12;
            for (int i = half + 1; i >= 0;) {
                scoreDigits.get(i).setPositionX(posX);
                posX -= 26;
                i--;
            }
            posX = DataHand.renderMan.getWidth() / 2 + 12;
            for (int i = half + 2; i < scoreDigits.size();) {
                scoreDigits.get(i).setPositionX(posX);
                posX += 26;
                i++;
            }
        }
        else {
            int half = scoreDigits.size() / 2 - 1;
            int posX = DataHand.renderMan.getWidth() / 2 - 24;
            for (int i = half; i >= 0;) {
                scoreDigits.get(i).setPositionX(posX);
                posX -= 24;
                i--;
            }
            posX = DataHand.renderMan.getWidth() / 2;
            for (int i = half + 1; i < scoreDigits.size();) {
                scoreDigits.get(i).setPositionX(posX);
                posX += 24;
                i++;
            }
        }

    }

    public static void upScore() {
        for (int i = scoreDigits.size() - 1; i >= 0;) {
            if (scoreDigits.get(i).getNumber() < 9) {
                scoreDigits.get(i).setNumber(scoreDigits.get(i).getNumber() + 1);
                break;
            }
            else if (i == 0) {
                scoreDigits.get(i).setNumber(1);
                centerDigit();
                break;
            }
            else {
                scoreDigits.get(i).setNumber(0);
            }
            i--;
        }
        score++;

        HighScoreDisplay.checkHighScore(score);
    }

    public static int getScore() {
        return score;
    }

    public static void reset() {
        for (int i = scoreDigits.size() - 1; i >= 0; i--) {
            scoreDigits.get(i).destroy();
            scoreDigits.remove(i);
        }
        addDigit();
        score = 0;
    }

}
