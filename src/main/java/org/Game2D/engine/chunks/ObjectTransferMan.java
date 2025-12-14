package org.Game2D.engine.chunks;

import org.Game2D.engine.objects.GameObject;

public class ObjectTransferMan {

    public void checkForTransfer(GameObject object) {
        Chunk targetChunk = ChunkMan.targetChunkByPosition(object);

    }

}
