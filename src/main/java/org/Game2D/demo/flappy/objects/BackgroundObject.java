package org.Game2D.demo.flappy.objects;

import org.Game2D.engine.objects.advanced.StaticObject;

import java.awt.*;

public class BackgroundObject extends StaticObject {

    public BackgroundObject(Rectangle hb, boolean collision, Image txt) {
        super(collision, hb, 0, txt);
    }
}
