package de.Game2D.engine.objects.advanced;

import de.Game2D.engine.core.Instance;
import de.Game2D.engine.objects.GameObject;
import de.Game2D.engine.objects.ObjectConfig;

import java.awt.*;

public abstract class StaticObject extends GameObject {

    public StaticObject(ObjectConfig config) {
        super(config.getInstance(), new Rectangle(config.positionX, config.positionY, config.hitBoxWith, config.hitBoxHeight), config.collision);
    }

    @Override
    public void update() {}

}
