package org.Game2D.engine.objects.advanced;

import org.Game2D.engine.objects.GameObject;
import org.Game2D.engine.physics.AdvancedPropertySet;
import org.Game2D.engine.physics.HitBox;

public abstract class DynamicObject extends GameObject {

    public AdvancedPropertySet advancedPropertySet = new AdvancedPropertySet();

    public DynamicObject(HitBox hitbox) {
        super(hitbox);
    }

    public abstract void update();

}
