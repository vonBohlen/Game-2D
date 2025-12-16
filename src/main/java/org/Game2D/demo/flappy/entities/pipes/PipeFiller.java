package org.Game2D.demo.flappy.entities.pipes;

import org.Game2D.demo.flappy.entities.Bird;
import org.Game2D.engine.core.handlers.DataHand;
import org.Game2D.engine.objects.advanced.Entity;
import org.Game2D.engine.utils.AssetMan;

import java.awt.*;

public class PipeFiller extends Entity {

    public PipeFiller(int x, int y, boolean belowPipe) {
        super(new Rectangle(x, belowPipe ? 0 : y + 612, 104, belowPipe ? y : DataHand.renderMan.getHeight() - (y + 612)), true, 2, AssetMan.loadAsset("flappy_assets/pipe/pipe-green-filler.png"));
    }

    @Override
    public void update() {

        if(Bird.gameOver) return;

        Bird.gameOver = move(-Bird.speed, 0) != null;
    }

    public void moveFiller(){
        move(-Bird.speed, 0);
    }

    public void adjust(int x, int y, boolean belowPipe){
        this.hitBox.x = x;
        this.hitBox.y = belowPipe ? 0 : y + 612;
        this.hitBox.width = 104;
        this.hitBox.height = belowPipe ? y : DataHand.renderMan.getHeight() - (y + 612);
    }

    @Override
    public void render(Graphics2D g2) {
        g2.drawImage(texture, hitBox.x, hitBox.y, hitBox.width, hitBox.height, null);
    }
}
