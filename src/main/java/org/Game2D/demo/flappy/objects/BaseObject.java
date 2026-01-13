package org.Game2D.demo.flappy.objects;

import org.Game2D.engine.objects.advanced.StaticObject;

import java.awt.*;

public class BaseObject extends StaticObject {

    public BaseObject(Rectangle hb, boolean collision, Image txt) {
        super(collision, hb, 3, txt);
    }

}
