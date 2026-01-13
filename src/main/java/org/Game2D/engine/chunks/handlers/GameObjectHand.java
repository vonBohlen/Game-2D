package org.Game2D.engine.chunks.handlers;

import lombok.NonNull;
import org.Game2D.engine.chunks.Chunk;
import org.Game2D.engine.chunks.manager.ChunkMan;
import org.Game2D.engine.events.handlers.objects.ObjectCreationHandler;
import org.Game2D.engine.events.handlers.objects.ObjectDeletionHandler;
import org.Game2D.engine.objects.GameObject;

public class GameObjectHand implements ObjectCreationHandler, ObjectDeletionHandler {

    @Override
    public void handelObjectCreationEvent(@NonNull GameObject gameObject) {
        Chunk chunk = ChunkMan.ChunkFromCoordinates(gameObject.hitBox.x, gameObject.hitBox.y);
        chunk.addGameObject(gameObject);
    }

    @Override
    public void handelObjectDeletionEvent(@NonNull GameObject gameObject) {
        Chunk chunk = ChunkMan.getChunkFromObject(gameObject);
        chunk.removeGameObject(gameObject);
    }

}
