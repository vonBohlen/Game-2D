package org.Game2D.demo.flappy;

import org.Game2D.demo.flappy.entities.ScoreDigit;
import org.Game2D.engine.core.handlers.DataHand;
import org.Game2D.engine.core.managers.RenderMan;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ScoreDisplay {

    private static int score = 0;

    public static Image one, two, three, four, five, six, seven, eight, nine, zero;

    private static final List<ScoreDigit> scoreDigits = new ArrayList<>();

    public static void init() {

        try {
            one = ImageIO.read(Objects.requireNonNull(RenderMan.class.getClassLoader().getResource("flappy_assets/numbers/1.png")));
            two = ImageIO.read(Objects.requireNonNull(RenderMan.class.getClassLoader().getResource("flappy_assets/numbers/2.png")));
            three = ImageIO.read(Objects.requireNonNull(RenderMan.class.getClassLoader().getResource("flappy_assets/numbers/3.png")));
            four = ImageIO.read(Objects.requireNonNull(RenderMan.class.getClassLoader().getResource("flappy_assets/numbers/4.png")));
            five = ImageIO.read(Objects.requireNonNull(RenderMan.class.getClassLoader().getResource("flappy_assets/numbers/5.png")));
            six = ImageIO.read(Objects.requireNonNull(RenderMan.class.getClassLoader().getResource("flappy_assets/numbers/6.png")));
            seven = ImageIO.read(Objects.requireNonNull(RenderMan.class.getClassLoader().getResource("flappy_assets/numbers/7.png")));
            eight = ImageIO.read(Objects.requireNonNull(RenderMan.class.getClassLoader().getResource("flappy_assets/numbers/8.png")));
            nine = ImageIO.read(Objects.requireNonNull(RenderMan.class.getClassLoader().getResource("flappy_assets/numbers/9.png")));
            zero = ImageIO.read(Objects.requireNonNull(RenderMan.class.getClassLoader().getResource("flappy_assets/numbers/0.png")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void start() {
        scoreDigits.add(new ScoreDigit(new Rectangle(DataHand.renderMan.getWidth() / 2 - 12, 10, 24, 36)));
    }

    private static void addDigit() {

        start();
        if (scoreDigits.size() % 2 != 0) {
            int half = scoreDigits.size() / 2;
            int posX = DataHand.renderMan.getWidth() / 2 - 12;
            for (int i = half; i >= 0;) {
                scoreDigits.get(i).setPositionX(posX);
                posX -= 26;
                i--;
            }
            posX = DataHand.renderMan.getWidth() / 2 + 12;
            for (int i = scoreDigits.size() - 1; i > half - 1;) {
                scoreDigits.get(i).setPositionX(posX);
                posX += 26;
                i--;
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
            for (int i = scoreDigits.size() - 1; i > half;) {
                scoreDigits.get(i).setPositionX(posX);
                posX += 24;
                i--;
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
                addDigit();
                break;
            }
            else {
                scoreDigits.get(i).setNumber(0);
            }
            i--;
        }
        score++;
    }

    public static int getScore() {
        return score;
    }

    public static void reset() {
        for (ScoreDigit digit : scoreDigits) scoreDigits.remove(digit);
        score = 0;
    }

}
