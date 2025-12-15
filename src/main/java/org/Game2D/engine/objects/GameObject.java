package org.Game2D.engine.objects;

import org.Game2D.engine.core.handlers.DataHand;

import java.awt.*;
import java.util.UUID;

public abstract class GameObject {

    public final Rectangle hitBox;
    public final UUID uuid;
    public int objectLayer;
    public boolean render_enabled;
    protected Image texture;
    private boolean collisionActivated = false;

    public GameObject(Rectangle hb, boolean collision, int objectLayer, boolean render_enabled, Image txt) {

        if (hb != null && collision) collisionActivated = true;
        hitBox = hb;

        this.objectLayer = objectLayer;

        texture = txt;

        uuid = UUID.randomUUID();

        this.render_enabled = render_enabled;

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

    public abstract void render(Graphics2D g2);

}
