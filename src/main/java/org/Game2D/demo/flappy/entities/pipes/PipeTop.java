package org.Game2D.demo.flappy.entities.pipes;

import org.Game2D.demo.flappy.entities.Bird;
import org.Game2D.engine.objects.advanced.Entity;
import org.Game2D.engine.utils.AssetMan;

import java.awt.*;

public class PipeTop extends Entity {

    public PipeTop(int x, int y) {
        super(new Rectangle(x, y, 104, 612), true, AssetMan.loadAsset("flappy_assets/pipe/pipe-green.png"));
    }

    @Override
    public void update() {

        if(Bird.gameOver) return;

        move(-Bird.speed, 0);
    }

    @Override
    public void draw(Graphics2D g2) {
        g2.drawImage(texture, hitBox.x, hitBox.y, hitBox.width, hitBox.height, null);
    }
}
