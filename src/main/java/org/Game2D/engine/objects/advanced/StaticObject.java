/**
 * @author The Game2D contributors
 */

package org.Game2D.engine.objects.advanced;

import org.Game2D.engine.objects.GameObject;

import java.awt.*;

public abstract class StaticObject extends GameObject {

    public StaticObject(Rectangle hitbox, boolean collision, int objectLayer, Image texture) {
        super(true, collision, hitbox, objectLayer, texture);
    }

    public StaticObject(Rectangle hitbox, boolean collision, int objectLayer) {
        super(true, collision, hitbox, objectLayer);
    }

    @Override
    public void update() {
    }

}
