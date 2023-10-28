package de.Alienlive.games.Game2D.objects.entities;

import de.Alienlive.games.Game2D.core.Instance;
import de.Alienlive.games.Game2D.core.KeyHandler;
import de.Alienlive.games.Game2D.core.ActionManager;
import de.Alienlive.games.Game2D.core.RenderManager;
import de.Alienlive.games.Game2D.objects.entities.entity.Entity;

import java.awt.*;

public class Square extends Entity {

    public Square(int pX, int pY, int pHeight, int pWidth, Instance i) {
        super(pX, pY, pHeight, pWidth, i);
    }

    public void update() {}

    public void draw(Graphics2D g2) {

        g2.setColor(Color.WHITE);

        g2.fillRect(this.box.x, this.box.y, 48, 48);

    }

}
