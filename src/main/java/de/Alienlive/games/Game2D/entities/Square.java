package de.Alienlive.games.Game2D.entities;

import de.Alienlive.games.Game2D.core.KeyHandler;
import de.Alienlive.games.Game2D.core.manager.ActionManager;
import de.Alienlive.games.Game2D.core.manager.RenderManager;
import de.Alienlive.games.Game2D.entities.entity.Entity;

import java.awt.*;

public class Square extends Entity {

    public HitBox box;
    public Square(int pX, int pY, int pHeight, int pWidth, RenderManager pRender, ActionManager pAction, KeyHandler pKey) {
        super(pX, pY, pHeight, pWidth, pRender, pAction, pKey);
    }

    public void setDefaultValues() {

        /*this.box = new HitBox(new Edge(200, 300),
                new Edge(this.box.TopLeft.x + 48, this.box.TopLeft.y),
                new Edge(this.box.TopLeft.x, this.box.TopLeft.y + 48),
                new Edge(this.box.TopLeft.x + 48, this.box.TopLeft.y + 48));*/

        this.box = new HitBox();
        this.box.TopLeft = new Edge(200, 300);
        this.box.TopRight = new  Edge(this.box.TopLeft.x + 48, this.box.TopLeft.y);
        this.box.DownLeft =new Edge(this.box.TopLeft.x, this.box.TopLeft.y + 48);
        this.box.DownRight = new Edge(this.box.TopLeft.x + 48, this.box.TopLeft.y + 48);

        speed = 0;

    }

    private void UpdateOtherEdges(){
        this.box.TopRight = new Edge(this.box.TopLeft.x + 48, this.box.TopLeft.y);
        this.box.DownLeft = new Edge(this.box.TopLeft.x, this.box.TopLeft.y + 48);
        this.box.DownRight = new Edge(this.box.TopLeft.x + 48, this.box.TopLeft.y + 48);
    }

    public void update() {

    }

    public void draw(Graphics2D g2) {

        g2.setColor(Color.WHITE);

        g2.fillRect(this.box.TopLeft.x, this.box.TopLeft.y, 48, 48);

    }

}
