package org.Game2D.engine.chunks;

import org.Game2D.engine.objects.GameObject;

/**
 * Object Transfer Manager
 * Moves GameObjects around and changes their Chunk if needed
 */
public class ObjectTransferMan {

    /**
     * Check if it is necessary to move the GameObject to another Chunk
     *
     * @param object GameObject to be checked
     * @param newX   New x-Coordinate
     * @param newY   New y-Coordinate
     * @return chunkTransferIsNecessary
     */
    private static boolean chunkTransferIsNecessary(GameObject object, int newX, int newY) {
        return ChunkMan.ChunkFromCoordinates(object.hitBox.x, object.hitBox.y) != ChunkMan.ChunkFromCoordinates(newX, newY);
    }

    /**
     * Transfer GameObject to new global coordinates
     *
     * @param object GameObject to be transferred
     * @param newX   New x-Coordinate
     * @param newY   New y-Coordinate
     */
    public static void transferAbs(GameObject object, int newX, int newY) {
        if (chunkTransferIsNecessary(object, newX, newY)) {
            object.hitBox.x = newX;
            object.hitBox.y = newY;

            Chunk old_chunk = ChunkMan.ChunkFromCoordinates(object.hitBox.x, object.hitBox.y);
            Chunk new_chunk = ChunkMan.ChunkFromCoordinates(newX, newY);
            moveObjectsInChunks(object, old_chunk, new_chunk);
        } else {
            object.hitBox.x = newX;
            object.hitBox.y = newY;
        }
    }

    /**
     * Transfer GameObject relative to its current coordinates
     *
     * @param object GameObject to be transferred
     * @param relX   Relative change in the x-Coordinate
     * @param relY   Relative change in te y-Coordinate
     */
    public static void transferRel(GameObject object, int relX, int relY) {
        transferAbs(object, object.hitBox.x + relX, object.hitBox.y + relY);
    }

    /**
     * Move a GameObject from old_chunk to new_chunk
     *
     * @param object    GameObject to be transferred
     * @param old_chunk Old Chunk
     * @param new_chunk New Chunk
     */
    private static void moveObjectsInChunks(GameObject object, Chunk old_chunk, Chunk new_chunk) {
        new_chunk.addGameObject(object);
        old_chunk.removeGameObject(object);
    }

}
