package org.Game2D.engine.events.events;

import lombok.Getter;
import org.Game2D.engine.events.handlers.UiElementEventHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

/**
 * Event utility class for ui element related events.
 */
public class UiElementEvents {

    // List of registered handlers
    @Getter
    private static final List<UiElementEventHandler> handlers = Collections.synchronizedList(new ArrayList<>());

    /**
     * Adds a handler to ui element related events.
     *
     * @param handler Handler to be added
     */
    public static void addHandler(UiElementEventHandler handler) {
        synchronized (handlers) {
            handlers.add(handler);
        }
    }

    /**
     * Removes a handler from ui element related events.
     *
     * @param handler Handler to be removed
     */
    public static void removeHandler(UiElementEventHandler handler) {
        synchronized (handlers) {
            handlers.remove(handler);
        }
    }

    /**
     * Calls an ui element related event.
     *
     * @param action Event call
     */
    public static void callEvent(Consumer<UiElementEventHandler> action) {
        synchronized (handlers) {
            handlers.forEach(action);
        }
    }

}
