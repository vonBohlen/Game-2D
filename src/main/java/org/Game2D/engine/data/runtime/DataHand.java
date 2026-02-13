/**
 * /engine/data/runtime/DataHand.java
 *
 * DESCRIPTION
 *
 * Copyright (C) 2026 Christian von Bohlen
 */

package org.Game2D.engine.data.runtime;

import org.Game2D.engine.audio.loops.AudioLoop;
import org.Game2D.engine.graphics.loops.RenderLoop;
import org.Game2D.engine.io.user.KeyHand;
import org.Game2D.engine.objects.loops.GameLoop;

import java.nio.file.Path;

public class DataHand {

    // Instance
    public static Instance instance = null;

    // Configuration path
    public static Path confPath = null;

    // Loops
    public static GameLoop actionLoop = null;
    public static RenderLoop renderLoop = null;
    public static AudioLoop audioLoop = null;

    // Handlers
    public static KeyHand keyHand = null;

}
