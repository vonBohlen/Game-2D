package de.Game2D.engine.objects;

import de.Game2D.engine.core.ActionManager;
import de.Game2D.engine.core.Instance;
import de.Game2D.engine.core.RenderManager;

import java.awt.*;

public abstract class GameObject {

    protected Instance instance;
    protected ActionManager actionManager;
    protected RenderManager renderManager;

    private boolean collisionActivated = true;
    public final Rectangle hitBox;

    public GameObject(Instance i, Rectangle hb) {
        instance = i;
        actionManager = i.getActionManager();
        renderManager = i.getRenderManager();

        if (hb == null) collisionActivated = false;
        hitBox = hb;

        actionManager.registerGameObject(this);
    }

    public boolean getCollisionActivated() {
        return collisionActivated;
    }

    public abstract void update();
    public abstract void draw(Graphics2D g2);
}
