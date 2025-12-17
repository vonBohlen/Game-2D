package org.Game2D.engine.objects;

import org.Game2D.engine.core.handlers.DataHand;
import org.Game2D.engine.core.managers.Camera;

import java.awt.*;
import java.util.UUID;

public abstract class GameObject {

    public final Rectangle hitBox;
    public final UUID uuid;
    public final int objectLayer;
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

    public void render(Graphics2D g2){
        g2.drawImage(texture, getScreenCoordinateX(), getScreenCoordinateY(), getScreenSpaceWidth(), getScreenSpaceHeight(), null);
    }
    public void drawHitBox(Graphics g2){
        g2.draw3DRect(getScreenCoordinateX(), getScreenCoordinateY(), getScreenSpaceWidth(), getScreenSpaceHeight(), false);
    }

    protected int getScreenCoordinateX(){
        return (hitBox.x * Camera.pixelsPerUnit) - (Camera.getScreenSpacePosX());
    }
    protected int getScreenCoordinateY(){
        return (hitBox.y * Camera.pixelsPerUnit) - (Camera.getScreenSpacePosY());
    }
    protected int getScreenSpaceWidth(){
        return hitBox.width * Camera.pixelsPerUnit;
    }
    protected int getScreenSpaceHeight(){
        return hitBox.height * Camera.pixelsPerUnit;
    }

}
