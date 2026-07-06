/**
 * /engine/data/runtime/Instance.java
 *
 * DESCRIPTION
 *
 * Copyright (C) 2026 Christian von Bohlen, J. K.
 */

package org.Game2D.engine.data.runtime;

import org.Game2D.engine.audio.loops.AudioLoop;
import org.Game2D.engine.chunks.handlers.GameObjectHand;
import org.Game2D.engine.chunks.managers.ChunkMan;
import org.Game2D.engine.data.disk.conf.ConfManager;
import org.Game2D.engine.errors.EngineErrorHand;
import org.Game2D.engine.events.events.EngineErrorEvents;
import org.Game2D.engine.events.events.GameObjectEvents;
import org.Game2D.engine.graphics.loops.RenderLoop;
import org.Game2D.engine.io.user.KeyHand;
import org.Game2D.engine.objects.loops.GameLoop;
import org.Game2D.tools.debug.DebugScreen;

import javax.swing.*;
import java.awt.*;

public class Instance {

    //Window
    private JFrame window;

    /**
     * Implements basic Window Logic
     * Hardware acceleration on Wayland with NVIDIA Graphics is
     * not working properly at this time
     * To use hardware acceleration, uncomment the given section in
     * the class constructor
     */
    public Instance() {

        // Initializing config system
        ConfManager.loadConf();
        ConfManager.loadDefaultConf();
        ConfManager.writeConf();

        // IMPORTANT
        // Hardware acceleration is known to cause problems on wayland while using NVIDIA graphic cards

        // Hardware acceleration
        if (ConfManager.getConfValueAsBool("game2d.game_loop.enable_hardware_acceleration"))
            System.setProperty("sun.java2d.opengl", "true");
        else
            System.setProperty("sun.java2d.opengl", "false");

        // Error listeners
        EngineErrorEvents.addHandler(new EngineErrorHand());

        // Starting the chunk system first, to prevent errors in other systems
        ChunkMan.initialize();

        DataHand.keyHand = new KeyHand();
        DataHand.renderLoop = new RenderLoop();
        DataHand.actionLoop = new GameLoop();
        DataHand.audioLoop = new AudioLoop();

        DataHand.renderLoop.initializeCamera();
        DebugScreen.addDefaultDebugParameters();
    }

    /**
     * Create a new window
     *
     * @param windowTitle Window title
     */
    public void start(String windowTitle) {

        //Loading Window
        loadWindow(windowTitle);

        // Listeners
        GameObjectEvents.addHandler(new GameObjectHand());

        //Starting Manager loops
        DataHand.renderLoop.startRenderLoop();
        DataHand.actionLoop.startGameLoop();
        DataHand.audioLoop.startAudioThread();

    }

    /**
     * Initialize the window with basic settings
     * Called as part of window creation
     *
     * @param windowTitle Window title
     */
    private void loadWindow(String windowTitle) {

        window = new JFrame();

        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setExtendedState(Frame.MAXIMIZED_BOTH);
        window.setUndecorated(true);
        window.setResizable(true);
        window.setTitle(windowTitle);
        window.setIgnoreRepaint(true);

        window.add(DataHand.renderLoop);

        window.pack();

        window.setLocationRelativeTo(null);
        window.setVisible(true);
        //window.setAlwaysOnTop(true);

    }

    /**
     * Spin down all Managers and close the window
     */
    public void exit() {
        DataHand.actionLoop.exit();
        DataHand.renderLoop.exit();
        DataHand.audioLoop.exit();

        window.dispose();
    }

}
