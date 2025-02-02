package org.Game2D.v1.engine.chunks;

import org.Game2D.v1.engine.objects.GameObject;

import java.util.List;
import java.util.Vector;

public class ChunkFactory {

    public static Chunk generateChunk(int posX, int posY, List<Integer> ids, List<GameObject> gameObjects) {
        Vector<Integer> id = new Vector<>(posX, posY);
        id.addAll(ids);
        return new Chunk(id, gameObjects);
    }

}
