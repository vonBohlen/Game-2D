package de.Alienlive.games.Game2D.core;

import de.Alienlive.games.Game2D.debug.DebugDisplay;

import javax.swing.*;

public class Instance {

    private final RenderManager renderManager;
    private final ActionManager actionManager;
    private final KeyHandler keyHandler;
    private final DebugDisplay debugDisplay;

    public Instance() {
        keyHandler = new KeyHandler();
        renderManager = new RenderManager();
        actionManager = new ActionManager();
        debugDisplay = new DebugDisplay(renderManager, actionManager);
    }

    public void start(String windowTitle) {
        //TODO: add Window seize option
        loadWindow(windowTitle);

        renderManager.startRenderThread();
        actionManager.startActionThread();
    }

    private void loadWindow(String windowTitle) {
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setExtendedState(window.MAXIMIZED_BOTH);
        window.setUndecorated(true);
        window.setResizable(false);
        window.setTitle(windowTitle);

        window.add(renderManager);

        window.pack();

        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }

}
