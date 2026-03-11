/**
 * /demo/flappy/objects/BaseObject.java
 *
 * DESCRIPTION
 *
 * Copyright (C) 2025 Christian von Bohlen
 */

package org.Game2D.demo.flappy.objects;

import org.Game2D.engine.graphics.Texture;
import org.Game2D.engine.objects.advanced.StaticObject;

import java.awt.*;

public class BaseObject extends StaticObject {

    public BaseObject(Rectangle hb, boolean collision, Image txt) {
        super(collision, hb, 3, new Texture(0, 0, txt), "base");
    }

}
