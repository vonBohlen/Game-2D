package de.Game2D.engine.core;

import de.Game2D.engine.debug.DebugDisplay;
import de.Game2D.engine.objects.ObjectConfig;

import javax.swing.*;

public class Instance {

    private final RenderManager renderManager;
    private final ActionManager actionManager;
    private final KeyHandler keyHandler;
    private final DebugDisplay debugDisplay;

    public Instance() {
        keyHandler = new KeyHandler();
        renderManager = new RenderManager(this);
        actionManager = new ActionManager(this);
        //TODO: create UI elements
        ObjectConfig debugDisplayConfig = new ObjectConfig(this);
        debugDisplay = new DebugDisplay(debugDisplayConfig);
    }

    public void start(String windowTitle) {
        //TODO: add Window seize option
        loadWindow(windowTitle);

        renderManager.startRenderThread();
        actionManager.startActionThread();
    }

    JFrame window;

    private void loadWindow(String windowTitle) {
        window = new JFrame();
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

    public int getWidth() {
        return window.getWidth();
    }

    public int getHeight() {
        return window.getHeight();
    }

    public KeyHandler getKeyHandler() {
        return keyHandler;
    }

    public RenderManager getRenderManager() {
        return renderManager;
    }

    public ActionManager getActionManager() {
        return actionManager;
    }

    public DebugDisplay getDebugDisplay() {
        return debugDisplay;
    }

}
