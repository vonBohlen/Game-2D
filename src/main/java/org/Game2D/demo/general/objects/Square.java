package org.Game2D.demo.general.objects;

import org.Game2D.engine.objects.advanced.StaticObject;

import java.awt.*;

public class Square extends StaticObject {

    public Square(Rectangle hb, Image txt) {
        super(hb, true, txt);
    }

    public void draw(Graphics2D g2) {

        g2.setColor(Color.WHITE);

        g2.drawImage(texture, hitBox.x, hitBox.y, hitBox.width, hitBox.height, null);

    }

}
