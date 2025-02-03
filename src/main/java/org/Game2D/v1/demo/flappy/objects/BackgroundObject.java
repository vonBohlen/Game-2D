package org.Game2D.v1.demo.flappy.objects;

import org.Game2D.v1.engine.objects.advanced.StaticObject;

import java.awt.*;

public class BackgroundObject extends StaticObject {

    public BackgroundObject(Rectangle hb, boolean collision, Image txt) {
        super(hb, collision, 0, txt);
    }

    @Override
    public void render(Graphics2D g2) {

        g2.drawImage(texture, hitBox.x, hitBox.y, hitBox.width, hitBox.height, null);

    }

}
