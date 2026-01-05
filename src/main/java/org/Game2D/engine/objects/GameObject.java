/**
 * @author The Game2D contributors
 */

package org.Game2D.engine.objects;

import org.Game2D.engine.core.handlers.DataHand;
import org.Game2D.engine.core.managers.Camera;
import org.Game2D.engine.utils.AssetMan;

import java.awt.*;
import java.util.UUID;

public abstract class GameObject {

    public Rectangle hitBox;
    public final UUID uuid;
    public int objectLayer;
    public boolean render_enabled;
    protected Image texture;
    private boolean collisionActivated = false;

    private void init(Rectangle hitbox, boolean collision, int objectLayer, boolean render_enabled) {

        if (hitbox != null && collision) collisionActivated = true;
        this.hitBox = hitbox;
        this.objectLayer = objectLayer;
        this.render_enabled = render_enabled;
        DataHand.regGameObj(this);
    }

    public GameObject(Rectangle hitBox, boolean collision, int objectLayer, boolean render_enabled, Image texture) {
        uuid = UUID.randomUUID();
        init(hitBox, collision, objectLayer, render_enabled);
        this.texture = texture;
    }

    public GameObject(Rectangle hitBox, boolean collision, int objectLayer, boolean render_enabled) {
        uuid = UUID.randomUUID();
        init(hitBox, collision, objectLayer, render_enabled);
        texture = AssetMan.loadAsset("default.png");
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
        return (int)(hitBox.x * Camera.pixelsPerUnit) - (Camera.getScreenSpacePosX());
    }
    protected int getScreenCoordinateY(){
        return (int)(hitBox.y * Camera.pixelsPerUnit) - (Camera.getScreenSpacePosY());
    }
    protected int getScreenCoordinateX( int offset){
        return (int)((hitBox.x + offset) * Camera.pixelsPerUnit) - (Camera.getScreenSpacePosX());
    }
    protected int getScreenCoordinateY(int offset){
        return (int)((hitBox.y + offset) * Camera.pixelsPerUnit) - (Camera.getScreenSpacePosY());
    }
    protected int getScreenSpaceWidth(){
        return (int)(hitBox.width * Camera.pixelsPerUnit);
    }
    protected int getScreenSpaceHeight(){
        return (int)(hitBox.height * Camera.pixelsPerUnit);
    }
    protected int getCustomScreenSpace(int value){
        return (int)(value * Camera.pixelsPerUnit);
    }

}
