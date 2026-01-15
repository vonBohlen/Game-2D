package org.Game2D.engine.events.handlers;

import lombok.NonNull;
import org.Game2D.engine.objects.GameObject;

/**
 * Event handler interface for object related events.
 */
public interface GameObjectEventHandler {

    /**
     * Gets called in case of an object creation event.
     *
     * @param gameObject New object
     */
    void handelObjectCreationEvent(@NonNull GameObject gameObject);

    /**
     * Gets called in case of an object deletion event.
     *
     * @param gameObject Deleted object
     */
    void handelObjectDeletionEvent(@NonNull GameObject gameObject);

}
