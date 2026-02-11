package org.Game2D.engine.ui.handlers;

import lombok.NonNull;
import org.Game2D.engine.events.handlers.UiElementEventHandler;
import org.Game2D.engine.ui.elements.UiElement;
import org.Game2D.engine.ui.managers.UiMan;

public class UiHand implements UiElementEventHandler {

    @Override
    public void handelUiElementCreationEvent(@NonNull UiElement uiElement) {
        UiMan.registerUiElement(uiElement);
    }

    @Override
    public void handelUiElementDeletionEvent(@NonNull UiElement uiElement) {
        UiMan.unregisterUiElement(uiElement);
    }

}
