/**
 * /engine/chunks/Chunk.java
 *
 * The Chunk class is part of the global collision system and stores GameObject in layers.
 *
 * Copyright (C) 2026 Silas Vogel, J. K., Christian von Bohlen
 */

package org.Game2D.engine.chunks;

import lombok.NonNull;
import org.Game2D.engine.chunks.managers.ChunkMan;
import org.Game2D.engine.graphics.Camera;
import org.Game2D.engine.objects.GameObject;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

// TODO: Fix texture overlapping between adjacent chunks

// TODO: Feature idea: calculate the parallelismThreshold for rendering / updating by the number of objects contained within the chunk

/**
 * The Chunk class is part of the global collision system and stores GameObject in layers.
 */
public class Chunk {

    /**
     * Identifier to enable easy differentiation between Chunks.
     */
    public final UUID UUID = java.util.UUID.randomUUID();

    /**
     * X and Y coordinates of the Chunk.
     */
    public final int POS_X, POS_Y;

    /**
     * HashMap of object layers, witch contain the GameObjects stored in the Chunk.
     */
    public final ConcurrentHashMap<Integer, ConcurrentHashMap<UUID, GameObject>> objectsByLayer = new ConcurrentHashMap<>();

    /**
     * Create a new Chunk with the specified coordinates
     *
     * @param posX x-Coordinate
     * @param posY y-Coordinate
     */
    public Chunk(int posX, int posY) {
        this.POS_X = posX;
        this.POS_Y = posY;
    }

    /**
     * Get a specific object layer from the Chunk.
     *
     * @param layerID ID of the wanted layer
     *
     * @return Wanted layer
     */
    public @NonNull ConcurrentHashMap<UUID, GameObject> getLayer(int layerID) {
        if (objectsByLayer.containsKey(layerID)) return objectsByLayer.get(layerID);
        return new ConcurrentHashMap<>();
    }

    /**
     * Add a GameObject to this Chunk
     *
     * @param object GameObject to be added
     */
    public void addGameObject(@NonNull GameObject object) {

        if (objectsByLayer.containsKey(object.layerID)) {
                objectsByLayer.get(object.layerID).put(object.UUID, object);
        }
        else {
            ConcurrentHashMap<UUID, GameObject> layer  = new ConcurrentHashMap<>();
            layer.put(object.UUID, object);
            objectsByLayer.put(object.layerID, layer);
        }

        ChunkMan.registerObject(object, this);
    }

    /**
     * Remove a GameObject from this Chunk
     *
     * @param object Object to be removed
     */
    public void removeGameObject(@NonNull GameObject object) {

        if (objectsByLayer.containsKey(object.layerID)) {
            objectsByLayer.get(object.layerID).remove(object.UUID);
            if (objectsByLayer.get(object.layerID).isEmpty()) {
                objectsByLayer.remove(object.layerID);
            }
        }

        ChunkMan.unregisterObject(object);
    }

    /**
     * Update all GameObjects in the Chunk
     */
    public void update() {
        ArrayList<Integer> keys = new ArrayList<>();
        objectsByLayer.forEachKey(Integer.MAX_VALUE, keys::add);
        Collections.sort(keys);
        for (Integer key : keys) {
            if (objectsByLayer.containsKey(key)) {
                for (Map.Entry<UUID, GameObject> entry : objectsByLayer.get(key).entrySet()) {
                    entry.getValue().update();
                }
            }
        }
    }

    /**
     * Render the GameObjects contained within the Chunk
     *
     * @param g2 Graphics instance to render to
     * @param renderHitBoxes Render the hitboxes of the GameObjects
     */
    public void render(@NonNull Graphics2D g2, boolean renderHitBoxes) {

        g2.setColor(new Color(0, 200, 50));
        ArrayList<Integer> keys = new ArrayList<>();
        objectsByLayer.forEachKey(Integer.MAX_VALUE, keys::add);
        Collections.sort(keys);
        GameObject object;
        for (Integer key : keys) {
            if (objectsByLayer.containsKey(key)) {
                for (Map.Entry<UUID, GameObject> entry : objectsByLayer.get(key).entrySet()) {
                    object = entry.getValue();
                    if (object.renderEnabled) object.render(g2);
                    if (renderHitBoxes) object.renderHitbox(g2);
                }
            }
        }
    }

    /**
     * Render the border of the Chunk.
     *
     * @param g2 Graphics instance to render to
     */
    public void renderBorder(@NonNull Graphics2D g2) {

        if (!objectsByLayer.isEmpty()) g2.setColor(new Color(150, 100, 200));
        else g2.setColor(new Color(0, 150, 200));

        g2.draw3DRect(
                (int) (POS_X * ChunkMan.chunkSize * Camera.pixelsPerUnit) - Camera.getScreenSpacePosX(),
                (int) (POS_Y * ChunkMan.chunkSize * Camera.pixelsPerUnit) - Camera.getScreenSpacePosY(),
                (int) (ChunkMan.chunkSize * Camera.pixelsPerUnit),
                (int) (ChunkMan.chunkSize * Camera.pixelsPerUnit),
                false
        );

    }

}
