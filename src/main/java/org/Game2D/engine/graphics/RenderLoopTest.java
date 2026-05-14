package org.Game2D.engine.graphics;

import org.Game2D.engine.graphics.loops.RenderLoopReplacement;

public class RenderLoopTest {

    public static void main(String[] args) {
        RenderLoopReplacement renderLoop = new RenderLoopReplacement();
        renderLoop.initialize();
        renderLoop.start();
    }

}
