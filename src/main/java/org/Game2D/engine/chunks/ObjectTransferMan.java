package org.Game2D.engine.chunks;

import org.Game2D.engine.objects.GameObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Object Transfer Manager
 * Moves GameObjects around and changes their Chunk if needed
 */
public class ObjectTransferMan {

    private static List<GameObject> transferQueue = new ArrayList<>();

    public static void prepareObjectTransfer(){
        transferQueue = new ArrayList<>();
    }

    public static void transferQueue(){
        for(GameObject object : transferQueue){
            transferObject(object);
        }
    }

    public static void transferObject(GameObject object){
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
    private static void moveObjectsInChunks(GameObject object, Chunk old_chunk, Chunk new_chunk) {
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
    private static boolean chunkTransferIsNecessary(GameObject object, int newX, int newY) {
        int size = ChunkMan.chunkSize;
        return !(object.hitBox.x / size == newX / size && object.hitBox.y / size == newY / size);
    }

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
