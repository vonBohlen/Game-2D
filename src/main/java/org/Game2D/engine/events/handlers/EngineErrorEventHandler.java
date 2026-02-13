/**
 * /engine/events/handlers/EngineErrorEventHandler.java
 *
 * Event handler interface for engine error related events.
 *
 * Copyright (C) 2026 Christian von Bohlen
 */

package org.Game2D.engine.events.handlers;

import javax.annotation.Nullable;
import java.lang.reflect.Method;

/**
 * Event handler interface for engine error related events.
 */
public interface EngineErrorEventHandler {

    void handelFatalEngineError(@Nullable Class<?> errorSourceClass, @Nullable Method errorSourceMethod, int errorCode, String errorMessage, boolean showDialog);

    void handelEngineError(@Nullable Class<?> errorSourceClass, @Nullable Method errorSourceMethod, int errorCode, String errorMessage, boolean showDialog);

    void handelEngineWarning(@Nullable Class<?> errorSourceClass, @Nullable Method errorSourceMethod, int errorCode, String errorMessage, boolean showDialog);

}
