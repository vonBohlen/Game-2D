package de.Alienlive.games.Game2D.debug;

import de.Alienlive.games.Game2D.core.manager.ActionManager;
import de.Alienlive.games.Game2D.core.manager.RenderManager;
import de.Alienlive.games.Game2D.entities.entity.Entity;

import java.awt.*;

public class FpsDisplay extends Entity {

    int fps;

    public HitBox box;

    public FpsDisplay(int pX, int pY, int pHeight, int pWidth, RenderManager pRender, ActionManager pAction, KeyHandler pKey) {
        super(pX, pY, pHeight, pWidth, pRender, pAction, pKey);
    }

    public void setDefaultValues() {

        this.box = new HitBox();
        this.box.TopLeft = new Edge(0, 0);
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

    public void update(int fps) {
        this.fps = fps;
    }

    public void draw(Graphics2D g2) {

        g2.setColor(Color.RED);

        g2.drawString("FPS: " + this.fps, this.box.TopLeft.x, this.box.TopLeft.y);

    }

}
