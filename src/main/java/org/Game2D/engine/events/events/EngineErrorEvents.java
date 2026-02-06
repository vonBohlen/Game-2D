package org.Game2D.engine.events.events;

import lombok.Getter;
import org.Game2D.engine.events.handlers.EngineErrorEventHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

/**
 * Event utility class for engine error related events.
 */
public class EngineErrorEvents {

    // List of registered handlers
    @Getter
    private static final List<EngineErrorEventHandler> handlers = Collections.synchronizedList(new ArrayList<>());


    /**
     * Adds a handler to engine error related events.
     *
     * @param handler Handler to be added
     */
    public static void addHandler(EngineErrorEventHandler handler) {
        synchronized (handlers) {
            handlers.add(handler);
        }
    }

    /**
     * Removes a handler from engine error related events.
     *
     * @param handler Handler to be removed
     */
    public static void removeHandler(EngineErrorEventHandler handler) {
        synchronized (handlers) {
            handlers.remove(handler);
        }
    }

    /**
     * Calls an engine error related event.
     *
     * @param action Event call
     */
    public static void callEvent(Consumer<EngineErrorEventHandler> action) {
        synchronized (handlers) {
            handlers.forEach(action);
        }
    }

}
