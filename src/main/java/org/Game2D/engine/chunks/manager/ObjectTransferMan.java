/**
 * Object Transfer Manager <br>
 * Moves GameObjects around and changes their Chunk if needed
 * @author The Game2D contributors
 */

package org.Game2D.engine.chunks.manager;

import lombok.NonNull;
import org.Game2D.engine.chunks.Chunk;
import org.Game2D.engine.objects.GameObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Object Transfer Manager
 * Moves GameObjects around and changes their Chunk if needed
 */
public class ObjectTransferMan {

    /**
     * Queue of GameObjects to be transferred
     */
    private static List<GameObject> transferQueue = new ArrayList<>();

    /**
     * Flush the current transfer queue to allow new GameObjects to be added
     */
    public static void prepareObjectTransfer() {
        transferQueue = new ArrayList<>();
    }

    /**
     * Execute all transfer operations in the transfer queue
     */
    public static void transferQueue() {
        for (GameObject object : transferQueue) {
            transferObject(object);
        }
    }

    /**
     * Move the specified GameObject based on the current
     * position and the wanted position
     *
     * @param object GameObject to be moved
     */
    private static void transferObject(@NonNull GameObject object) {
        Chunk new_chunk = ChunkMan.ChunkFromCoordinates(object.hitBox.x, object.hitBox.y);
        Chunk old_chunk = ChunkMan.getChunkFromObject(object);

        moveObjectsInChunks(object, old_chunk, new_chunk);
    }

    /**
     * Move a GameObject from old_chunk to new_chunk
     *
     * @param object    GameObject to be transferred
     * @param old_chunk Old Chunk
     * @param new_chunk New Chunk
     */
    private static void moveObjectsInChunks(GameObject object, @NonNull Chunk old_chunk, @NonNull Chunk new_chunk) {
        //first remove then add because if you add first the relation between chunk and object gets set to a new value but after that the object gets removed
        old_chunk.removeGameObject(object);
        new_chunk.addGameObject(object);

        //System.out.println("Moved GameObject " + object.uuid + " from Chunk x" + old_chunk.posX + " y" + old_chunk.posY + " to " + "Chunk x" + new_chunk.posX + " y" + new_chunk.posY);
    }

    /**
     * Check if it is necessary to move the GameObject to another Chunk
     *
     * @param object GameObject to be checked
     * @param newX   New x-Coordinate
     * @param newY   New y-Coordinate
     * @return chunkTransferIsNecessary
     */
    private static boolean chunkTransferIsNecessary(@NonNull GameObject object, int newX, int newY) {
        int size = ChunkMan.chunkSize;
        return !(object.hitBox.x / size == newX / size && object.hitBox.y / size == newY / size);
    }

    /**
     * Check if the object needs to be moved into a new Chunk after the MoveAbs
     * and if so, add it to the transfer queue
     *
     * @param object GameObject to be checked
     * @param oldX   Old x-Coordinate
     * @param oldY   Old y-Coordinate
     */
    public static void checkTransferAfterMoveAbs(GameObject object, int oldX, int oldY) {
        if (chunkTransferIsNecessary(object, oldX, oldY)) transferQueue.add(object);
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
}
