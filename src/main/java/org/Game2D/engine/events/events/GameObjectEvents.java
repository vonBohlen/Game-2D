package org.Game2D.engine.events.events;

import lombok.Getter;
import org.Game2D.engine.events.handlers.GameObjectEventHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

/**
 * Event utility class for object related events.
 */
public class GameObjectEvents {

    // List of registered handlers
    @Getter
    private static final List<GameObjectEventHandler> handlers = Collections.synchronizedList(new ArrayList<>());

    /**
     * Adds a handler to object related events.
     *
     * @param handler Handler to be added
     */
    public static void addHandler(GameObjectEventHandler handler) {
        synchronized (handlers) {
            handlers.add(handler);
        }
    }

    /**
     * Removes a handler from object related events.
     *
     * @param handler Handler to be removed
     */
    public static void removeHandler(GameObjectEventHandler handler) {
        synchronized (handlers) {
            handlers.remove(handler);
        }
    }

    /**
     * Calls an object related event.
     *
     * @param action Event call
     */
    public static void callEvent(Consumer<GameObjectEventHandler> action) {
        synchronized (handlers) {
            handlers.forEach(action);
        }
    }

}
