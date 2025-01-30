package org.Game2D.v1.demo.flappy.entities.pipes;

import org.Game2D.v1.demo.flappy.entities.Bird;
import org.Game2D.v1.engine.objects.advanced.Entity;
import org.Game2D.v1.engine.utils.AssetMan;

import java.awt.*;

public class PipeBelow extends Entity {

    PipeFiller filler;
    public PipeBelow(int x, int y) {
        super(new Rectangle(x, y, 104, 628), true, 2, AssetMan.loadAsset("flappy_assets/pipe/pipe-green-upside-down.png"));
        filler = new PipeFiller(x, y, true);
    }

    @Override
    public void update() {

        if(Bird.gameOver) return;

        Bird.gameOver = move(-Bird.speed, 0) == null ? false : true;

        if(Bird.gameOver){filler.moveFiller();}
    }

    public void adjustFiller(){
        filler.adjust(this.hitBox.x, this.hitBox.y, true);
    }

    @Override
    public void draw(Graphics2D g2) {
        g2.drawImage(texture, hitBox.x, hitBox.y, hitBox.width, hitBox.height, null);
    }
}
