/**
 * /engine/events/handlers/UiElementHandler.java
 *
 * Event handler interface for ui element related events.
 *
 * Copyright (C) 2026 Christian von Bohlen
 */

package org.Game2D.engine.events.handlers;

import lombok.NonNull;
import org.Game2D.engine.ui.elements.UiElement;

/**
 * Event handler interface for ui element related events.
 */
public interface UiElementEventHandler {

    /**
     * Gets called in case of an object creation event.
     *
     * @param uiElement New ui element
     */
    void handelUiElementCreationEvent(@NonNull UiElement uiElement);

    /**
     * Gets called in case of an object deletion event.
     *
     * @param uiElement Deleted ui element
     */
    void handelUiElementDeletionEvent(@NonNull UiElement uiElement);

}
