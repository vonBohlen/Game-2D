package org.Game2D.v1.demo.general.objects;

import org.Game2D.v1.engine.objects.advanced.StaticObject;

import java.awt.*;

public class Square extends StaticObject {

    public Square(Rectangle hb, Image txt) {
        super(hb, true, 0, txt);
    }

    public void render(Graphics2D g2) {

        g2.setColor(Color.WHITE);

        g2.drawImage(texture, hitBox.x, hitBox.y, hitBox.width, hitBox.height, null);

    }

}
