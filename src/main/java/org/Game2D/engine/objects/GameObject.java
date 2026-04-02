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
import org.Game2D.engine.data.disk.assets.AssetManager;
import org.Game2D.engine.graphics.Texture;

import javax.annotation.Nullable;
import java.awt.*;
import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

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

    // Textures
    public static final Texture PLACEHOLDER = new Texture(0, 0, AssetManager.getAsset("default.png"));
    private final ConcurrentHashMap<String, Texture> textures = new ConcurrentHashMap<>();

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

    }

    /**
     * Creates the fundamental data structure and key functionality for GameObjects.
     *
     * @param renderEnabled Flag for rendering
     * @param collisionEnabled Flag for collision
     * @param hitbox Hitbox for the GameObject
     * @param layerID Additional hitbox data for the layer of the GameObject
     * @param texture Texture for the GameObject
     * @param textureID ID for the texture
     */
    public GameObject(boolean renderEnabled, boolean collisionEnabled, @NonNull Rectangle hitbox, int layerID, @NonNull Texture texture, @NonNull String textureID) {

        init(renderEnabled, collisionEnabled, hitbox, layerID);

        textures.put(textureID, texture);

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

        textures.put("PLACEHOLDER", PLACEHOLDER);

    }

    /**
     * Gets called by the GameLoop according to targetTPS, and can therefor be overridden to manipulate the GameObject.
     */
    public abstract void update();

    /**
     * Adds the GameObjects's render data to the provided Graphics2D.
     *
     * @param g2 Java.awt Graphics2D, to add render data to
     */
    public void render(Graphics2D g2){

        textures.forEachValue(Integer.MAX_VALUE, texture -> {
            g2.drawImage(texture.image, getScreenCoordinateX() + texture.offsetX, getScreenCoordinateY() + texture.offsetY, getScreenSpaceWidth(), getScreenSpaceHeight(), null);
        });

    }

    /**
     * Adds the GameObjects's hitbox render data to the provided Graphics.
     *
     * @param g Java.awt Graphics, to add render data to
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

    public void addTexture(String id, Texture texture) {
        textures.put(id, texture);
        textures.remove("PLACEHOLDER");
    }

    public @Nullable Texture getTexture(String id) {
        return textures.get(id);
    }

    public ArrayList<Texture> getTextures() {
        ArrayList<Texture> allTextures = new ArrayList<>();
        textures.forEachValue(Integer.MAX_VALUE, allTextures::add);
        return allTextures;
    }

    public void removeTexture(String id) {
        if (textures.size() <= 1) textures.put("PLACEHOLDER", PLACEHOLDER);
        textures.remove(id);
    }

    public void register() {
        // Call object creation event
        GameObjectEvents.callEvent(
                handler ->
                        handler.handelObjectCreationEvent(
                                this
                        )
        );
    }

    /**
     * Removes the GameObject from the engine.
     */
    public void unregister() {

        // Call objec deletion event
        GameObjectEvents.callEvent(
                handler ->
                        handler.handelObjectDeletionEvent(
                                this
                        )
        );

    }

}
