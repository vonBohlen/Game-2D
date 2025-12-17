package org.Game2D.demo.general.entities;

import org.Game2D.engine.core.handlers.DataHand;
import org.Game2D.engine.core.managers.ActionMan;
import org.Game2D.engine.objects.advanced.Entity;

import java.awt.*;

public class Ball extends Entity {

    public Ball(Rectangle hb, Image txt) {
        super(hb, true, 0, txt);
    }

    @Override
    public void update(){

        if (hitBox.y >= DataHand.renderMan.getHeight()) setPosition(0, 0, false);
        //if (ActionMan.getGameTick() != 30 && ActionMan.getGameTick() != 60) return;

        int x = hitBox.x + 1;
        int y = x / 2;

        move(50, y);

    }
}
