package de.Alienlive.games.Game2D.entities;

import de.Alienlive.games.Game2D.core.manager.ActionManager;
import de.Alienlive.games.Game2D.core.manager.RenderManager;
import de.Alienlive.games.Game2D.entities.entity.Entity;

import java.awt.*;

public class Player extends Entity {

    public HitBox box;
    public Player(int pX, int pY, int pHeight, int pWidth, RenderManager pRender, ActionManager pAction, KeyHandler pKey) {
        super(pX, pY, pHeight, pWidth, pRender, pAction, pKey);
    }


    private void UpdateOtherEdges(){
        this.box.TopRight = new Edge(this.box.TopLeft.x + 48, this.box.TopLeft.y);
        this.box.DownLeft = new Edge(this.box.TopLeft.x, this.box.TopLeft.y + 48);
        this.box.DownRight = new Edge(this.box.TopLeft.x + 48, this.box.TopLeft.y + 48);
    }

    public void update() {

        CheckCollision();

        if (!(this.box.TopLeft.x - speed <= -1)) {
            if (keyHandler.leftPressed) this.box.TopLeft.x -= speed;
        }
        else this.box.TopLeft.x = 0;

        if (!(this.box.TopLeft.x + speed >= (renderManager.getWidth() - 47))) {
            if (keyHandler.rightPressed) this.box.TopLeft.x += speed;
        }
            else this.box.TopLeft.x = (renderManager.getWidth() - 48);

        if (!(this.box.TopLeft.y - speed <= -1)) {
            if (keyHandler.upPressed) this.box.TopLeft.y -= speed;
        }
            else this.box.TopLeft.y = 0;

        if (!(this.box.TopLeft.y + speed >= (renderManager.getHeight() - 47))) {
            if (keyHandler.downPressed) this.box.TopLeft.y += speed;
        }
            else {this.box.TopLeft.y = (renderManager.getHeight() - 48);}

        UpdateOtherEdges();
    }

    public void CheckCollision(){

        HitBox IncBox = IncreaseEdgeRadius(this.box);
        if(keyHandler.upPressed){

        }
        if (keyHandler.rightPressed){

        }
        if(keyHandler.leftPressed){

        }
        if(keyHandler.downPressed){

        }
    }

    public HitBox IncreaseEdgeRadius(HitBox obj){
        return new HitBox(new Edge(obj.TopLeft.x - 1, obj.TopLeft.y -1), new Edge(obj.TopRight.x + 1, obj.TopRight.y -1), new Edge(obj.DownLeft.x - 1, obj.DownLeft.y +1), new Edge(obj.DownRight.x +1, obj.DownRight.y +1));
    }

    public void draw(Graphics2D g2) {

        g2.setColor(Color.WHITE);

        g2.fillRect(this.box.TopLeft.x, this.box.TopLeft.y, 48, 48);

    }


}
