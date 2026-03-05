/**
 * /engine/chunks/handlers/GameObjectHand.java
 *
 * Event handler for game object related events, of the chunk system
 *
 * Copyright (C) 2026 Christian von Bohlen
 */

package org.Game2D.engine.chunks.handlers;

import lombok.NonNull;
import org.Game2D.engine.chunks.Chunk;
import org.Game2D.engine.chunks.managers.ChunkMan;
import org.Game2D.engine.events.handlers.GameObjectEventHandler;
import org.Game2D.engine.objects.GameObject;

public class GameObjectHand implements GameObjectEventHandler {

    @Override
    public void handelObjectCreationEvent(@NonNull GameObject gameObject) {
        Chunk chunk = ChunkMan.chunkFromCoordinates(gameObject.hitbox.x, gameObject.hitbox.y);
        chunk.addGameObject(gameObject);
    }

    @Override
    public void handelObjectDeletionEvent(@NonNull GameObject gameObject) {
        Chunk chunk = ChunkMan.getChunkFromObject(gameObject);
        chunk.removeGameObject(gameObject);
    }

}
