package org.Game2D.engine.events.events.objects;

import lombok.Getter;
import lombok.NonNull;
import org.Game2D.engine.events.handlers.objects.ObjectCreationHandler;
import org.Game2D.engine.objects.GameObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ObjectCreationEvent {

    @Getter
    private static final List<ObjectCreationHandler> handlers = Collections.synchronizedList(new ArrayList<>());

    public static void addHandler(ObjectCreationHandler handler) {
        synchronized (handlers) {
            handlers.add(handler);
        }
    }

    public static void removeHandler(ObjectCreationHandler handler) {
        synchronized (handlers) {
            handlers.remove(handler);
        }
    }

    public static void callEvent(@NonNull GameObject gameObject) {
        synchronized (handlers) {
            handlers.forEach(handler -> handler.handelObjectCreationEvent(gameObject));
        }
    }

}
