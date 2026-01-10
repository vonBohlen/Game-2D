/**
 * @author The Game2D contributors
 */

package org.Game2D.demo.flappy.entities.pipes;

import org.Game2D.demo.flappy.entities.Bird;
import org.Game2D.engine.objects.advanced.Entity;
import org.Game2D.engine.io.assets.AssetMan;

import java.awt.*;

public class PipeBelow extends Entity {

    final PipeFiller filler;
    public PipeBelow(int x, int y) {
        super(new Rectangle(x, y, 104, 628), true, 2, AssetMan.loadAsset("flappy_assets/pipe/pipe-green-upside-down.png"));
        filler = new PipeFiller(x, y, true);
    }

    @Override
    public void update() {

        if(Bird.gameOver) return;

        Bird.gameOver = move(-Bird.speed, 0) != null;

        if(Bird.gameOver){filler.moveFiller();}
    }

    public void adjustFiller(){
        filler.adjust(this.hitBox.x, this.hitBox.y, true);
    }
}
