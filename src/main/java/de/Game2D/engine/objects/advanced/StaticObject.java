package de.Game2D.engine.objects.advanced;

import de.Game2D.engine.core.Instance;
import de.Game2D.engine.objects.GameObject;

import java.awt.*;

public abstract class StaticObject extends GameObject {

    public StaticObject(Instance i, Rectangle hitBox) {
        super(i, hitBox);
    }

}
