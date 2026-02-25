package org.Game2D.engine.graphics.loops;

import org.Game2D.engine.graphics.opengl.GLFWManager;
import org.Game2D.engine.graphics.opengl.Window;

public class RenderLoopReplacement implements Runnable {

    private boolean run = false;

    public static Window window;

    public static void initialize() {
        GLFWManager.initGLFW();
        window = new Window("test");
    }


    public static void start() {
        if (!GLFWManager.isGLFW_INIT() || window == null) return;
    }

    @Override
    public void run() {

    }
}
