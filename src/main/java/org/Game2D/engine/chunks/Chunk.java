package org.Game2D.engine.chunks;

import org.Game2D.engine.objects.GameObject;

import java.awt.*;
import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Chunk class that stores a HashMap of GameObjects
 * Used by the ChunkManager
 */
public class Chunk {
    public final UUID uuid;

    /**
     * HashMap of GameObjects in this Chunk identified by their UUID
     */
    public final ConcurrentHashMap<UUID, GameObject> objects = new ConcurrentHashMap<>();
    public final java.util.List<GameObject> objectsByLayers = new ArrayList<>();
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
        objects.put(object.uuid, object);
        ChunkMan.registerObject(object, this);
    }

    /**
     * Remove a GameObject from this Chunk
     *
     * @param object Object to be removed
     */
    public void removeGameObject(GameObject object) {
        objects.remove(object.uuid);
        ChunkMan.unregisterObject(object);
    }

    /**
     * Update all GameObjects in the Chunk
     */
    public void update() {
        for (GameObject object : objects.values()) {
            object.update();
        }
    }

    /**
     * Not implemented yet
     */
    public void render(Graphics2D g2, boolean renderHitBoxes, boolean renderChunk) {

        // render objects in chunk and their hitboxes
        g2.setColor(new Color(0,200,50));
        for(GameObject go : objects.values()){
            if (go.getTexture() != null || !go.render_enabled) go.render(g2);
            if (go.hitBox != null && renderHitBoxes)
                g2.draw3DRect(go.hitBox.x, go.hitBox.y, go.hitBox.width, go.hitBox.height, false);
        }

        //render the chunks outline if it contains an object
        if(renderChunk && !objects.isEmpty()){
            g2.setColor(new Color(0, 150, 200));
            g2.draw3DRect(posX * ChunkMan.chunkSize, posY * ChunkMan.chunkSize, ChunkMan.chunkSize, ChunkMan.chunkSize, false);
        }
    }


}
