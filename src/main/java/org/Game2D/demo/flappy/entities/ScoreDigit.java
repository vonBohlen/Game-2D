/**
 * @author The Game2D contributors
 */

package org.Game2D.demo.flappy.entities;

import lombok.Getter;
import org.Game2D.demo.flappy.ScoreDisplay;
import org.Game2D.engine.graphics.Texture;
import org.Game2D.engine.objects.advanced.Entity;

import java.awt.*;

public class ScoreDigit extends Entity {

    @Getter
    private int number = 0;

    private final Texture texture = new Texture(0, 0, ScoreDisplay.numbers.get(0));

    public ScoreDigit(Rectangle hb) {
        super(false, hb, 4);
        addTexture("digit", texture);
    }

    @Override
    public void update() {}

    public void setNumber(int number) {
        if (number > 9) return;
        this.number = number;

        switch (number) {
            case 1 -> texture.image = ScoreDisplay.numbers.get(9);
            case 2 -> texture.image = ScoreDisplay.numbers.get(8);
            case 3 -> texture.image = ScoreDisplay.numbers.get(7);
            case 4 -> texture.image = ScoreDisplay.numbers.get(6);
            case 5 -> texture.image = ScoreDisplay.numbers.get(5);
            case 6 -> texture.image = ScoreDisplay.numbers.get(4);
            case 7 -> texture.image = ScoreDisplay.numbers.get(3);
            case 8 -> texture.image = ScoreDisplay.numbers.get(2);
            case 9 -> texture.image = ScoreDisplay.numbers.get(1);
            case 0 -> texture.image = ScoreDisplay.numbers.get(0);
        }
    }

    public void setPositionX(int posX) {
        hitbox.x = posX;
    }

    public void remove() {
        delete();
    }

}
