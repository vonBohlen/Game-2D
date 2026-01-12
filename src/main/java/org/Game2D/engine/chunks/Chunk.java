/**
 * Chunk class that stores a HashMap of GameObjects Used by the ChunkManager
 * @author The Game2D contributors
 */

package org.Game2D.engine.chunks;

import org.Game2D.engine.chunks.manager.ChunkMan;
import org.Game2D.engine.graphics.Camera;
import org.Game2D.engine.objects.GameObject;
import lombok.NonNull;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.UUID;

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
    public java.util.List<GameObject> objectsByLayers = Collections.synchronizedList(new ArrayList<>());
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
        //objects.put(object.uuid, object);
        //TODO: Put GameObject at right place in List
        synchronized (objectsByLayers) {
            objectsByLayers.add(object);
        }
        ChunkMan.registerObject(object, this);
    }

    /**
     * Remove a GameObject from this Chunk
     *
     * @param object Object to be removed
     */
    public void removeGameObject(GameObject object) {
        //objects.remove(object.uuid);
        synchronized (objectsByLayers) {
            objectsByLayers.remove(object);
        }
        ChunkMan.unregisterObject(object);
    }

    /**
     * Update all GameObjects in the Chunk
     */
    public void update() {
        synchronized (objectsByLayers) {
            for (GameObject object : objectsByLayers) {
                object.update();
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
    public void render(@NonNull Graphics2D g2, boolean renderHitBoxes, boolean renderChunk) {

        synchronized (objectsByLayers) {
            // setRenderData objects in chunk and their hitboxes
            g2.setColor(new Color(0, 200, 50));
            for (GameObject go : objectsByLayers) {
                if (go.getTexture() != null || !go.renderEnabled) go.setRenderData(g2);
                if (go.hitBox != null && renderHitBoxes) go.setHitboxRenderData(g2);
            }
        }

        synchronized (objectsByLayers) {
            //setRenderData the chunks outline if it contains an object
            if (renderChunk && !objectsByLayers.isEmpty()) {
                g2.setColor(new Color(0, 150, 200));
                g2.draw3DRect((int) (posX * ChunkMan.chunkSize * Camera.pixelsPerUnit) - Camera.getScreenSpacePosX(), (int) (posY * ChunkMan.chunkSize * Camera.pixelsPerUnit) - Camera.getScreenSpacePosY(), (int) (ChunkMan.chunkSize * Camera.pixelsPerUnit), (int) (ChunkMan.chunkSize * Camera.pixelsPerUnit), false);
            }
        }
    }


}
