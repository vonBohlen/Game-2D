/**
 * /engine/chunks/Chunk.java
 *
 * Chunk class that stores a HashMap of GameObjects Used by the ChunkManager
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
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

// TODO: Fix texture overlapping between adjacent chunks

// TODO: Feature idea: calculate the parallelismThreshold for rendering / updating by the number of objects contained within the chunk

/**
 * Chunk class that stores a HashMap of GameObjects
 * Used by the ChunkManager
 */
public class Chunk {

    public final UUID uuid;

    public final int posX;
    public final int posY;

    /**
     * HashMap of GameObjects in this Chunk identified by their UUID
     */
    public final ConcurrentHashMap<Integer, ConcurrentHashMap<UUID, GameObject>> objectsByLayer = new ConcurrentHashMap<>();

    // Process times
    public final ConcurrentHashMap<GameObject, Long> updateTimes = new ConcurrentHashMap<>();
    public final ConcurrentHashMap<GameObject, Long> renderTimes = new ConcurrentHashMap<>();

    /**
     * Create a new Chunk with the specified coordinates
     *
     * @param posX x-Coordinate
     * @param posY y-Coordinate
     */
    public Chunk(int posX, int posY) {
        this.posX = posX;
        this.posY = posY;

        uuid = UUID.randomUUID();
    }

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

        if (objectsByLayer.containsKey(object.LAYER_ID)) {
                objectsByLayer.get(object.LAYER_ID).put(object.uuid, object);
        }
        else {
            ConcurrentHashMap<UUID, GameObject> layer  = new ConcurrentHashMap<>();
            layer.put(object.uuid, object);
            objectsByLayer.put(object.LAYER_ID, layer);
        }

        ChunkMan.registerObject(object, this);
    }

    /**
     * Remove a GameObject from this Chunk
     *
     * @param object Object to be removed
     */
    public void removeGameObject(@NonNull GameObject object) {

        if (objectsByLayer.containsKey(object.LAYER_ID)) {
            objectsByLayer.get(object.LAYER_ID).remove(object.uuid);
            if (objectsByLayer.get(object.LAYER_ID).isEmpty()) {
                objectsByLayer.remove(object.LAYER_ID);
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
            if (key != null && objectsByLayer.containsKey(key)) {
                    objectsByLayer.get(key).forEachValue(Integer.MAX_VALUE, object -> {
                        updateTimes.remove(object);
                        long start = System.nanoTime();
                        object.update();
                        updateTimes.put(object, start - System.nanoTime());
                    });
                }
            }
        }

    /**
     * Render the hitboxes of the GameObjects and the Chunks
     *
     * @param g2             Graphics instance passed by the RenderManager
     * @param renderHitBoxes Render the hitboxes of the GameObjects?
     * @param renderChunkBorders    Render the bounding box of the Chunk?
     */
    public void setRenderData(@NonNull Graphics2D g2, boolean renderHitBoxes, boolean renderChunkBorders) {

        // setRenderData objects in chunk and their hitboxes
        g2.setColor(new Color(0, 200, 50));
        ArrayList<Integer> keys = new ArrayList<>();
        objectsByLayer.forEachKey(Integer.MAX_VALUE, keys::add);
        Collections.sort(keys);
        for (Integer key : keys) {
            if (key != null && objectsByLayer.containsKey(key)) {
                objectsByLayer.get(key).forEachValue(Integer.MAX_VALUE, object -> {
                    renderTimes.remove(object);
                    long start = System.nanoTime();
                    if (object.renderEnabled) object.setRenderData(g2);
                    if (renderHitBoxes) object.setHitBoxRenderData(g2);
                    renderTimes.put(object, start - System.nanoTime());
                });
            }
        }

        if (renderChunkBorders && !objectsByLayer.isEmpty()) {
            g2.setColor(new Color(150, 100, 200));
            g2.draw3DRect(
                    (int) (posX * ChunkMan.CHUNK_SIZE * Camera.pixelsPerUnit) - Camera.getScreenSpacePosX(),
                    (int) (posY * ChunkMan.CHUNK_SIZE * Camera.pixelsPerUnit) - Camera.getScreenSpacePosY(),
                    (int) (ChunkMan.CHUNK_SIZE * Camera.pixelsPerUnit),
                    (int) (ChunkMan.CHUNK_SIZE * Camera.pixelsPerUnit),
                    false
            );
        }
        else if (renderChunkBorders) {
            g2.setColor(new Color(0, 150, 200));
            g2.draw3DRect(
                    (int) (posX * ChunkMan.CHUNK_SIZE * Camera.pixelsPerUnit) - Camera.getScreenSpacePosX(),
                    (int) (posY * ChunkMan.CHUNK_SIZE * Camera.pixelsPerUnit) - Camera.getScreenSpacePosY(),
                    (int) (ChunkMan.CHUNK_SIZE * Camera.pixelsPerUnit),
                    (int) (ChunkMan.CHUNK_SIZE * Camera.pixelsPerUnit),
                    false
            );
        }

    }

}
