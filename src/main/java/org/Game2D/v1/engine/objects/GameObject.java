package org.Game2D.v1.engine.objects;

import org.Game2D.v1.engine.core.handlers.DataHand;

import java.awt.*;

public abstract class GameObject {

    private boolean collisionActivated = false;
    public final Rectangle hitBox;

    public int objectLayer = 0;

    protected Image texture;

    public GameObject(Rectangle hb, boolean collision, int objectLayer, Image txt) {

        if (hb != null && collision) collisionActivated = true;
        hitBox = hb;

        this.objectLayer = objectLayer;

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

    public Image getTexture() {
        return texture;
    }

    public void destroy() {
        DataHand.remGameObj(this);
    }

    public abstract void update();
    public abstract void draw(Graphics2D g2);

}
