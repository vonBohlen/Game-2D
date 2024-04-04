package de.Game2D.engine_old.objects.advanced;

import de.Game2D.engine_old.objects.GameObject;
import de.Game2D.engine_old.objects.ObjectConfig;

import java.awt.*;

public abstract class StaticObject extends GameObject {

    public StaticObject(ObjectConfig config) {
        super(config.getInstance(), new Rectangle(config.positionX, config.positionY, config.hitBoxWith, config.hitBoxHeight), config.collision, config.texture);
    }

    @Override
    public void update() {}

}
