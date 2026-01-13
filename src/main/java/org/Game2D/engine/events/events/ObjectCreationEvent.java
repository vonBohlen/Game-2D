package org.Game2D.engine.events.events;

import lombok.Getter;
import org.Game2D.engine.events.handlers.ObjectCreationHandler;
import org.Game2D.engine.objects.GameObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ObjectCreationEvent {

    @Getter
    private static final List<ObjectCreationHandler> handlers = Collections.synchronizedList(new ArrayList<>());

    public static void addListener(ObjectCreationHandler listener) {
        synchronized (handlers) {
            handlers.add(listener);
        }
    }

    public static void removeListener(ObjectCreationHandler listener) {
        synchronized (handlers) {
            handlers.remove(listener);
        }
    }

    public static void callEvent(GameObject gameObject) {
        synchronized (handlers) {
            handlers.forEach(handler -> handler.handelObjectCreationEvent(gameObject));
        }
    }

}
