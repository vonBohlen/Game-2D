/**
 * /engine/errors/EngineErrorHand.java
 *
 * DESCRIPTION
 *
 * Copyright (C) 2026 Christian von Bohlen
 */

package org.Game2D.engine.errors;

import org.Game2D.engine.events.handlers.EngineErrorEventHandler;

import javax.annotation.Nullable;
import java.lang.reflect.Method;

public class EngineErrorHand implements EngineErrorEventHandler {

    @Override
    public void handelFatalEngineError(@Nullable Class<?> errorSourceClass, @Nullable Method errorSourceMethod, int errorCode, String errorMessage, boolean showDialog) {
        if (showDialog) System.out.println(String.format("showDialog=%b", showDialog));
    }

    @Override
    public void handelEngineError(@Nullable Class<?> errorSourceClass, @Nullable Method errorSourceMethod, int errorCode, String errorMessage, boolean showDialog) {

    }

    @Override
    public void handelEngineWarning(@Nullable Class<?> errorSourceClass, @Nullable Method errorSourceMethod, int errorCode, String errorMessage, boolean showDialog) {

    }
}
