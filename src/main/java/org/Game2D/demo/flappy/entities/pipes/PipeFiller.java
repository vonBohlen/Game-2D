/**
 * @author The Game2D contributors
 */

package org.Game2D.demo.flappy.entities.pipes;

import org.Game2D.demo.flappy.entities.Bird;
import org.Game2D.engine.data.runtime.DataHand;
import org.Game2D.engine.objects.advanced.Entity;
import org.Game2D.engine.io.assets.AssetMan;

import java.awt.*;

public class PipeFiller extends Entity {

    public PipeFiller(int x, int y, boolean belowPipe) {
        super(new Rectangle(x, belowPipe ? 0 : y + 612, 104, belowPipe ? y : DataHand.renderLoop.getHeight() - (y + 612)), true, 2, AssetMan.loadAsset("flappy_assets/pipe/pipe-green-filler.png"));
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
        this.hitBox.height = belowPipe ? y : DataHand.renderLoop.getHeight() - (y + 612);
    }
}
