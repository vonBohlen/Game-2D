package org.Game2D.engine.chunks;

import org.Game2D.engine.objects.GameObject;
import org.Game2D.engine.chunks.Chunk;

import java.util.UUID;

public class ObjectTransferMan {

    private static boolean chunkTransferIsNecessary(GameObject object, int newX, int newY) {
        //TODO
    }

    public static void transferAbs(GameObject object, int newX, int newY) {
        if (chunkTransferIsNecessary(object, newX, newY)) {

        } else {
            object.hitBox.x = newX;
            object.hitBox.y = newY;
        }
    }

    public static void transferRel(GameObject object, int relX, int relY) {
        transferAbs(object, object.hitBox.x + relX, object.hitBox.y + relY);
    }

    private static void moveObjectsInChunks(GameObject object, Chunk old_chunk, Chunk new_chunk) {
        new_chunk.addGameObject(object);
        old_chunk.removeGameObject(object);
    }

}
