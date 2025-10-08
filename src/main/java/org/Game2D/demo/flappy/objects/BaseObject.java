package org.Game2D.demo.flappy.objects;

import org.Game2D.engine.objects.advanced.StaticObject;

import java.awt.*;

public class BaseObject extends StaticObject {

    public BaseObject(Rectangle hb, boolean collision, Image txt) {
        super(hb, collision, 3, txt);
    }

    @Override
    public void render(Graphics2D g2) {

        g2.drawImage(texture, hitBox.x, hitBox.y, hitBox.width, hitBox.height, null);

    }

}
