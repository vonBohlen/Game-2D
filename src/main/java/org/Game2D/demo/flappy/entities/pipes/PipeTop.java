/**
 * @author The Game2D contributors
 */

package org.Game2D.demo.flappy.entities.pipes;

import org.Game2D.demo.flappy.entities.Bird;
import org.Game2D.engine.data.disk.assets.AssetManager;
import org.Game2D.engine.graphics.Texture;
import org.Game2D.engine.objects.advanced.Entity;

import java.awt.*;

public class PipeTop extends Entity {

    public final PipeFiller filler;
    public PipeTop(int x, int y) {
        super(true, new Rectangle(x, y, 104, 612), 2, new Texture(0, 0, AssetManager.getAsset("flappy_assets/pipe/pipe-green.png")), "pipe_top");
        filler = new PipeFiller(x, y, false);
        register();
    }

    @Override
    public void update() {

        if(Bird.gameOver) return;

        Bird.gameOver = move(-Bird.speed, 0) != null;

        if(Bird.gameOver){filler.moveFiller();}
    }

    public void adjustFiller(){
        filler.adjust(this.hitbox.x, this.hitbox.y, false);
    }
}
