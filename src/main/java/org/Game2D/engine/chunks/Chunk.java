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

/**
 * Chunk class that stores a HashMap of GameObjects
 * Used by the ChunkManager
 */
public class Chunk {

    public final UUID uuid;

    //TODO: Ich habe hier jetzt die ConcurrentHashMap auskommentiert weil irgendwie wurde die Funktionalität anhand einer UUID ein Objekt zu finden garnicht verwendet...
    // Zusätzlich hat das immer fehler erzeugt aus irgendeinem Grund

    /**
     * HashMap of GameObjects in this Chunk identified by their UUID
     */
    //public final ConcurrentHashMap<UUID, GameObject> objects = new ConcurrentHashMap<>();
    public final ConcurrentHashMap<Integer, ConcurrentHashMap<UUID, GameObject>> objectsByLayers = new ConcurrentHashMap<>();
   // public final java.util.List<GameObject> objectsByLayers = Collections.synchronizedList(new ArrayList<>());
    public final int posX;
    public final int posY;

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

    /**
     * Add a GameObject to this Chunk
     *
     * @param object GameObject to be added
     */
    public void addGameObject(GameObject object) {

        if (objectsByLayers.containsKey(object.objectLayer)) {
                objectsByLayers.get(object.objectLayer).put(object.uuid, object);
        }
        else {
            ConcurrentHashMap<UUID, GameObject> layer  = new ConcurrentHashMap<>();
            layer.put(object.uuid, object);
            objectsByLayers.put(object.objectLayer, layer);
        }

        ChunkMan.registerObject(object, this);
    }

    /**
     * Remove a GameObject from this Chunk
     *
     * @param object Object to be removed
     */
    public void removeGameObject(GameObject object) {

        if (objectsByLayers.containsKey(object.objectLayer)) {
            objectsByLayers.get(object.objectLayer).remove(object.uuid);
            if (objectsByLayers.get(object.objectLayer).isEmpty()) {
                objectsByLayers.remove(object.objectLayer);
            }
        }

        ChunkMan.unregisterObject(object);
    }

    /**
     * Update all GameObjects in the Chunk
     */
    public void update() {
        ArrayList<Integer> keys = new ArrayList<>();
        objectsByLayers.forEachKey(1, keys::add);
        // TODO: sort the key list from lowest to highest integer
        for (Integer key : keys) {
            if (objectsByLayers.containsKey(key)) {
                    objectsByLayers.get(key).forEachValue(1, GameObject::update);
                }
            }
        }

    /**
     * Render the hitboxes of the GameObjects and the Chunks
     *
     * @param g2             Graphics instance passed by the RenderManager
     * @param renderHitBoxes Render the hitboxes of the GameObjects?
     * @param renderChunk    Render the bounding box of the Chunk?
     */
    public void setRenderData(@NonNull Graphics2D g2, boolean renderHitBoxes, boolean renderChunk) {

        synchronized (objectsByLayers) {
            // setRenderData objects in chunk and their hitboxes
            g2.setColor(new Color(0, 200, 50));
            ArrayList<Integer> keys = new ArrayList<>();
            objectsByLayers.forEachKey(1, keys::add);
            // TODO: sort the key list from lowest to highest integer
            for (Integer key : keys) {
                if (objectsByLayers.containsKey(key)) {
                    objectsByLayers.get(key).forEachValue(1, object -> {
                        if (object.renderEnabled) object.setRenderData(g2);
                        if (renderHitBoxes) object.setHitBoxRenderData(g2);
                    });
                }
            }
        }

        synchronized (objectsByLayers) {
            //setRenderData the chunks outline if it contains an object
            if (renderChunk && !objectsByLayers.isEmpty()) {
                g2.setColor(new Color(0, 150, 200));
                g2.draw3DRect((int) (posX * ChunkMan.CHUNK_SIZE * Camera.pixelsPerUnit) - Camera.getScreenSpacePosX(), (int) (posY * ChunkMan.CHUNK_SIZE * Camera.pixelsPerUnit) - Camera.getScreenSpacePosY(), (int) (ChunkMan.CHUNK_SIZE * Camera.pixelsPerUnit), (int) (ChunkMan.CHUNK_SIZE * Camera.pixelsPerUnit), false);
            }
        }
    }


}
