package de.Alienlive.games.Game2D.entities;

import de.Alienlive.games.Game2D.core.KeyHandler;
import de.Alienlive.games.Game2D.core.manager.ActionManager;
import de.Alienlive.games.Game2D.core.manager.RenderManager;
import de.Alienlive.games.Game2D.entities.entity.Entity;

import java.awt.*;

public class Player extends Entity {

    public Player(int pX, int pY, int pHeight, int pWidth, RenderManager pRender, ActionManager pAction, KeyHandler pKey) {
        super(pX, pY, pHeight, pWidth, pRender, pAction, pKey);
    }


    public void update() {
        if (keyHandler.keyPressed_W) {
            this.box.y -= 1;
        }
        if (keyHandler.keyPressed_S) {
            this.box.y += 1;
        }
        if (keyHandler.keyPressed_A) {
            this.box.x -= 1;
        }
        if (keyHandler.keyPressed_D) {
            this.box.x += 1;
        }
    }

    public void draw(Graphics2D g2) {

        g2.setColor(Color.WHITE);

        g2.fillRect(this.box.x, this.box.y, 48, 48);

    }


}
