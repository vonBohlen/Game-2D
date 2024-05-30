package de.Game2D.engine.objects;

import de.Game2D.engine.core.handlers.DataHand;

import java.awt.*;

public abstract class GameObject {

    private boolean collisionActivated = false;
    public final Rectangle hitBox;

    protected Image texture;

    public GameObject(Rectangle hb, boolean collision, Image txt) {

        if (hb != null && collision) collisionActivated = true;
        hitBox = hb;

        texture = txt;

        DataHand.regGameObj(this);
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
        DataHand.remGameObj(this);
    }

    public abstract void update();
    public abstract void draw(Graphics2D g2);

}
