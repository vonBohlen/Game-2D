package org.Game2D.engine.events.events.objects;

import lombok.Getter;
import lombok.NonNull;
import org.Game2D.engine.events.handlers.objects.ObjectDeletionHandler;
import org.Game2D.engine.objects.GameObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ObjectDeletionEvent {

    @Getter
    private static final List<ObjectDeletionHandler> handlers = Collections.synchronizedList(new ArrayList<>());

    public static void addHandler(ObjectDeletionHandler handler) {
        synchronized (handlers) {
            handlers.add(handler);
        }
    }

    public static void removeHandler(ObjectDeletionHandler handler) {
        synchronized (handlers) {
            handlers.remove(handler);
        }
    }

    public static void callEvent(@NonNull GameObject gameObject) {
        synchronized (handlers) {
            handlers.forEach(handler -> handler.handelObjectDeletionEvent(gameObject));
        }
    }

}
