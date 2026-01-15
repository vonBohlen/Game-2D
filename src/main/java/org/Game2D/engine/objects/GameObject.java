/**
 * @author The Game2D contributors
 */

package org.Game2D.engine.objects;

import lombok.NonNull;
import org.Game2D.engine.events.events.GameObjectEvents;
import org.Game2D.engine.graphics.Camera;
import org.Game2D.engine.io.assets.AssetMan;

import java.awt.*;
import java.util.UUID;

/**
 * The GameObject class provides the fundamental data structure and key functionality to all GameObjects within the engine.
 */
public abstract class GameObject {

    // Identifier
    public final UUID uuid = UUID.randomUUID();

    // Flags
    public boolean renderEnabled;
    public boolean collisionEnabled;

    // HitBox
    @NonNull public Rectangle hitBox; // TODO: Create custom Hitbox class
    public int objectLayer; // TODO: Move into HitBox

    // Texture
    @NonNull public Image texture = AssetMan.loadAsset("default.png");

    /**
     * Initializes all fields within the class.
     *
     * @param renderEnabled Flag for rendering
     * @param collisionEnabled Flag for collision
     * @param hitbox Hitbox for the GameObject
     * @param objectLayer Additional hitbox data for the layer of the GameObject
     */
    private void init(boolean renderEnabled, boolean collisionEnabled, @NonNull Rectangle hitbox, int objectLayer) {

        this.collisionEnabled = collisionEnabled;
        this.renderEnabled = renderEnabled;

        this.hitBox = hitbox;

        this.objectLayer = objectLayer;

        // Call object creation event
        GameObjectEvents.callEvent(handler -> handler.handelObjectCreationEvent(this));

    }

    /**
     * Creates the fundamental data structure and key functionality for GameObjects.
     *
     * @param renderEnabled Flag for rendering
     * @param collisionEnabled Flag for collision
     * @param hitbox Hitbox for the GameObject
     * @param objectLayer Additional hitbox data for the layer of the GameObject
     * @param texture Texture for the GameObject
     */
    public GameObject(boolean renderEnabled, boolean collisionEnabled, @NonNull Rectangle hitbox, int objectLayer, @NonNull Image texture) {

        init(renderEnabled, collisionEnabled, hitbox, objectLayer);

        this.texture = texture;

    }

    /**
     * Creates the fundamental data structure and key functionality for GameObjects.
     *
     * @param renderEnabled Flag for rendering
     * @param collisionEnabled Flag for collision
     * @param hitbox Hitbox for the GameObject
     * @param objectLayer Additional hitbox data for the layer of the GameObject
     */
    public GameObject(boolean renderEnabled, boolean collisionEnabled, @NonNull Rectangle hitbox, int objectLayer) {

        init(renderEnabled, collisionEnabled, hitbox, objectLayer);

    }

    /**
     * Gets called by the ActionLoop according to TPS, and can therefor be overridden to manipulate the GameObject.
     */
    public abstract void update();

    /**
     * Adds the GameObjects's render data to the provided Graphics2D.
     *
     * @param g2 Java.awt Graphics2D, to add render data to
     */
    public void setRenderData(Graphics2D g2){

        g2.drawImage(texture, getScreenCoordinateX(), getScreenCoordinateY(), getScreenSpaceWidth(), getScreenSpaceHeight(), null);

    }

    /**
     * Adds the GameObjects's hitbox render data to the provided Graphics.
     *
     * @param g Java.awt Graphics, to add render data to
     */
    public void setHitboxRenderData(Graphics g){

        g.draw3DRect(getScreenCoordinateX(), getScreenCoordinateY(), getScreenSpaceWidth(), getScreenSpaceHeight(), false);

    }

    // Experimental graphics stuff

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

    /**
     * Removes the GameObject from the engine.
     */
    public void delete() {

        // Call objec deletion event
        GameObjectEvents.callEvent(handler -> handler.handelObjectDeletionEvent(this));

    }

}
