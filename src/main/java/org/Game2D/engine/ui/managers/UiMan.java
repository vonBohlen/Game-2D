package org.Game2D.engine.ui.managers;

import lombok.NonNull;
import org.Game2D.engine.data.runtime.DataHand;
import org.Game2D.engine.ui.elements.UiElement;

import java.util.HashMap;

public class UiMan {

    private static final HashMap<Integer, UiElement> uiElements = new HashMap<>();

    public static void registerUiElement(@NonNull UiElement uiElement) {
        uiElements.put(0, uiElement);
    }

    public static void unregisterUiElement(@NonNull UiElement uiElement) {
        uiElements.remove(uiElement);
    }

    public static void test() {
        DataHand.renderLoop.getMousePosition();
    }

}
