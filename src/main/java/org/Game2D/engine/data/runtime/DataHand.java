/**
 * @author The Game2D contributors
 */

package org.Game2D.engine.data.runtime;

import org.Game2D.engine.audio.loops.AudioLoop;
import org.Game2D.engine.graphics.loops.RenderLoop;
import org.Game2D.engine.io.user.Keyhand;
import org.Game2D.engine.objects.loops.ActionLoop;

import java.nio.file.Path;

public class DataHand {

    // Instance
    public static Instance instance = null;

    // Configuration path
    public static Path confPath = null;

    // Loops
    public static ActionLoop actionLoop = null;
    public static RenderLoop renderLoop = null;
    public static AudioLoop audioLoop = null;

    // Handlers
    public static Keyhand keyHand = null;

}
