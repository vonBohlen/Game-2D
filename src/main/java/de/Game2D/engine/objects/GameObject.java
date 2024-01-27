package de.Game2D.engine.objects;

import de.Game2D.engine.core.ActionManager;
import de.Game2D.engine.core.Instance;
import de.Game2D.engine.core.RenderManager;

import java.awt.*;

public abstract class GameObject {

    protected Instance instance;
    protected ActionManager actionManager;
    protected RenderManager renderManager;

    private boolean collisionActivated = false;
    public final Rectangle hitBox;

    protected Image texture;

    public GameObject(Instance i, Rectangle hb, boolean collision, Image txt) {
        instance = i;
        actionManager = i.getActionManager();
        renderManager = i.getRenderManager();

        if (hb != null && collision) collisionActivated = true;
        hitBox = hb;

        texture = txt;

        actionManager.registerGameObject(this);
    }

    protected void activateCollision() {
        if (hitBox != null) collisionActivated = true;
    }

    protected void deactivateCollision() {
        collisionActivated = false;
    }

    public boolean getCollisionActivated() {
        return collisionActivated;
    }

    public void destroy() {
        actionManager.removeGameObject(this);
    }

    public abstract void update();
    public abstract void draw(Graphics2D g2);
}
