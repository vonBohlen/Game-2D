package org.Game2D.v1.engine.chunks;

import java.util.ArrayList;
import java.util.List;

public class ChunkManager {

    private static final List<Chunk> chunks = new ArrayList<>();

    //TODO: addChunk (chunk)

    //TODO: removeChunk (chunk)

    //TODO: TransferEntity (entity, origin, target)

    //TODO: existById (index, value)

    //TODO: existByPosition (posX, posY)

    //TODO: getChunkById (index, value)

    //TODO: getChunkByPosition (posX, posY)

    //TODO: updateChunksById (index, value)

    //TODO: updateChunksByPosition (posX, posY)

    //TODO: drawChunksById (index, value)

    //TODO: drawChunksByPosition (posX, posY)

    public static List<Chunk> getChunks() {
        return new ArrayList<>(chunks);
    }

}
