package org.Game2D.engine.events.handlers.objects;

import lombok.NonNull;
import org.Game2D.engine.objects.GameObject;

public interface ObjectDeletionHandler {

    void handelObjectDeletionEvent(@NonNull GameObject gameObject);

}
