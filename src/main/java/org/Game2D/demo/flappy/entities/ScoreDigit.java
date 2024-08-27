package org.Game2D.demo.flappy.entities;

import org.Game2D.demo.flappy.ScoreDisplay;
import org.Game2D.engine.objects.advanced.Entity;

import java.awt.*;

public class ScoreDigit extends Entity {

    private int number;

    public ScoreDigit(Rectangle hb) {
        super(hb, false, ScoreDisplay.zero);
    }

    @Override
    public void update() {}

    public void setNumber(int number) {
        if (number > 9) return;
        this.number = number;

        switch (number) {
            case 1 -> texture = ScoreDisplay.one;
            case 2 -> texture = ScoreDisplay.two;
            case 3 -> texture = ScoreDisplay.three;
            case 4 -> texture = ScoreDisplay.four;
            case 5 -> texture = ScoreDisplay.five;
            case 6 -> texture = ScoreDisplay.six;
            case 7 -> texture = ScoreDisplay.seven;
            case 8 -> texture = ScoreDisplay.eight;
            case 9 -> texture = ScoreDisplay.nine;
            case 0 -> texture = ScoreDisplay.zero;
        }
    }

    public int getNumber() {
        return number;
    }

    public void setPositionX(int posX) {
        hitBox.x = posX;
    }

    public void updateTxt(Image txt) {
        texture = txt;
    }

    @Override
    public void draw(Graphics2D g2) {

        g2.drawImage(texture, hitBox.x, hitBox.y, hitBox.width, hitBox.height, null);

    }

    public void remove() {
        destroy();
    }

}
