package org.Game2D.engine.objects;

import org.Game2D.engine.core.handlers.DataHand;

import java.awt.*;
import java.util.UUID;

public abstract class GameObject {

    private boolean collisionActivated = false;
    public final Rectangle hitBox;

    public int objectLayer = 0;

    protected Image texture;

    public final UUID uuid;

    public GameObject(Rectangle hb, boolean collision, int objectLayer, Image txt) {

        if (hb != null && collision) collisionActivated = true;
        hitBox = hb;

        this.objectLayer = objectLayer;

        texture = txt;

        DataHand.regGameObj(this);

        uuid = UUID.randomUUID();
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
    public abstract void render(Graphics2D g2);

}
