/**
 * /engine/objects/GameObject.java
 *
 * The GameObject class provides the fundamental data structure and key functionality to all GameObjects within the engine.
 *
 * Copyright (C) 2026 Christian von Bohlen, Silas Vogel
 */

package org.Game2D.engine.objects;

import lombok.NonNull;
import org.Game2D.engine.events.events.GameObjectEvents;
import org.Game2D.engine.graphics.Camera;
import org.Game2D.engine.data.disk.assets.AssetMan;

import java.awt.*;
import java.util.UUID;

/**
 * The GameObject class provides the fundamental data structure and key functionality to all GameObjects within the engine.
 */
public abstract class GameObject {

    // Identifier
    public final UUID UUID = java.util.UUID.randomUUID();

    // Flags
    public boolean renderEnabled;
    public boolean collisionEnabled;

    // Hitbox
    @NonNull public Rectangle hitbox; // TODO: Create custom Hitbox class
    public int layerID; // TODO: Move into HitBox

    // Texture
    @NonNull public Image texture = AssetMan.loadAsset("default.png");

    // Render offset
    public int renderOffsetX = 0;
    public int renderOffsetY = 0;

    /**
     * Initializes all fields within the class.
     *
     * @param renderEnabled Flag for rendering
     * @param collisionEnabled Flag for collision
     * @param hitbox Hitbox for the GameObject
     * @param layerID Additional hitbox data for the layer of the GameObject
     */
    private void init(boolean renderEnabled, boolean collisionEnabled, @NonNull Rectangle hitbox, int layerID) {

        this.collisionEnabled = collisionEnabled;
        this.renderEnabled = renderEnabled;

        this.hitbox = hitbox;
        this.layerID = layerID;

        // Call object creation event
        GameObjectEvents.callEvent(
                handler ->
                        handler.handelObjectCreationEvent(
                                this
                        )
        );

    }

    /**
     * Creates the fundamental data structure and key functionality for GameObjects.
     *
     * @param renderEnabled Flag for rendering
     * @param collisionEnabled Flag for collision
     * @param hitbox Hitbox for the GameObject
     * @param layerID Additional hitbox data for the layer of the GameObject
     * @param texture Texture for the GameObject
     */
    public GameObject(boolean renderEnabled, boolean collisionEnabled, @NonNull Rectangle hitbox, int layerID, @NonNull Image texture) {

        init(renderEnabled, collisionEnabled, hitbox, layerID);

        this.texture = texture;

    }

    /**
     * Creates the fundamental data structure and key functionality for GameObjects.
     *
     * @param renderEnabled Flag for rendering
     * @param collisionEnabled Flag for collision
     * @param hitbox Hitbox for the GameObject
     * @param layerID Additional hitbox data for the layer of the GameObject
     */
    public GameObject(boolean renderEnabled, boolean collisionEnabled, @NonNull Rectangle hitbox, int layerID) {

        init(renderEnabled, collisionEnabled, hitbox, layerID);

    }

    /**
     * Gets called by the GameLoop according to TARGET_TPS, and can therefor be overridden to manipulate the GameObject.
     */
    public abstract void update();

    /**
     * Adds the GameObjects's renderObject data to the provided Graphics2D.
     *
     * @param g2 Java.awt Graphics2D, to add renderObject data to
     */
    public void renderObject(Graphics2D g2){

        g2.drawImage(texture, getScreenCoordinateX() + renderOffsetX, getScreenCoordinateY() + renderOffsetY, getScreenSpaceWidth(), getScreenSpaceHeight(), null);

    }

    /**
     * Adds the GameObjects's hitbox renderObject data to the provided Graphics.
     *
     * @param g Java.awt Graphics, to add renderObject data to
     */
    public void renderHitbox(Graphics g){

        g.draw3DRect(getScreenCoordinateX(), getScreenCoordinateY(), getScreenSpaceWidth(), getScreenSpaceHeight(), false);

    }

    // Experimental graphics stuff

    protected int getScreenCoordinateX(){
        return (int)(hitbox.x * Camera.pixelsPerUnit) - (Camera.getScreenSpacePosX());
    }
    protected int getScreenCoordinateY(){
        return (int)(hitbox.y * Camera.pixelsPerUnit) - (Camera.getScreenSpacePosY());
    }
    protected int getScreenCoordinateX( int offset){
        return (int)((hitbox.x + offset) * Camera.pixelsPerUnit) - (Camera.getScreenSpacePosX());
    }
    protected int getScreenCoordinateY(int offset){
        return (int)((hitbox.y + offset) * Camera.pixelsPerUnit) - (Camera.getScreenSpacePosY());
    }
    protected int getScreenSpaceWidth(){
        return (int)(hitbox.width * Camera.pixelsPerUnit);
    }
    protected int getScreenSpaceHeight(){
        return (int)(hitbox.height * Camera.pixelsPerUnit);
    }
    protected int getCustomScreenSpace(int value){
        return (int)(value * Camera.pixelsPerUnit);
    }

    /**
     * Removes the GameObject from the engine.
     */
    public void delete() {

        // Call objec deletion event
        GameObjectEvents.callEvent(
                handler ->
                        handler.handelObjectDeletionEvent(
                                this
                        )
        );

    }

}
