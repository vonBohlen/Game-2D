/**
 * @author The Game2D contributors
 */

package org.Game2D.demo.flappy.entities;

import org.Game2D.demo.flappy.ScoreDisplay;
import org.Game2D.engine.objects.advanced.Entity;

import java.awt.*;

public class ScoreDigit extends Entity {

    private int number = 0;

    public ScoreDigit(Rectangle hb) {
        super(false, hb, 4, ScoreDisplay.numbers.get(0));
    }

    @Override
    public void update() {}

    public void setNumber(int number) {
        if (number > 9) return;
        this.number = number;

        switch (number) {
            case 1 -> texture = ScoreDisplay.numbers.get(9);
            case 2 -> texture = ScoreDisplay.numbers.get(8);
            case 3 -> texture = ScoreDisplay.numbers.get(7);
            case 4 -> texture = ScoreDisplay.numbers.get(6);
            case 5 -> texture = ScoreDisplay.numbers.get(5);
            case 6 -> texture = ScoreDisplay.numbers.get(4);
            case 7 -> texture = ScoreDisplay.numbers.get(3);
            case 8 -> texture = ScoreDisplay.numbers.get(2);
            case 9 -> texture = ScoreDisplay.numbers.get(1);
            case 0 -> texture = ScoreDisplay.numbers.get(0);
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

    public void remove() {
        destroy();
    }

}
