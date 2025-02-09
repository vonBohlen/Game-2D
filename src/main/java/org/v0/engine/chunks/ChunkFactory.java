package org.Game2D.v0.engine.chunks;

import org.Game2D.v0.engine.objects.GameObject;

import java.util.List;
import java.util.Vector;

public class ChunkFactory {

    public static Chunk generateChunk(int posX, int posY, int size, List<Integer> ids, List<GameObject> gameObjects) {
        Vector<Integer> id = new Vector<>();
        id.add(posX);
        id.add(posY);
        id.add(size);
        id.addAll(ids);
        return new Chunk(id, gameObjects);
    }

}
