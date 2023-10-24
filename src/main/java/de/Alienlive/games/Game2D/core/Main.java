package de.Alienlive.games.Game2D.core;

import de.Alienlive.games.Game2D.core.manager.ActionManager;
import de.Alienlive.games.Game2D.core.manager.RenderManager;
import de.Alienlive.games.Game2D.debug.DebugDisplay;
import de.Alienlive.games.Game2D.entities.Player;

import javax.swing.*;

public class Main {

    private static RenderManager renderManager;
    private static ActionManager actionManager;
    private static KeyHandler keyHandler;
    private static DebugDisplay debugDisplay;

    public static void main(String[] args) {

        keyHandler = new KeyHandler();
        renderManager = new RenderManager();
        actionManager = new ActionManager();

        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setExtendedState(window.MAXIMIZED_BOTH);
        window.setUndecorated(true);
        window.setResizable(false);
        window.setTitle("2D Game");

        window.add(renderManager);

        window.pack();

        window.setLocationRelativeTo(null);
        window.setVisible(true);

        debugDisplay = new DebugDisplay(renderManager, actionManager);

        Player player = new Player(200, 200, 48, 48, renderManager, actionManager, keyHandler);

        renderManager.startRenderThread();
        actionManager.startActionThread();

    }

    public static RenderManager getRenderManager() {
        return renderManager;
    }

    public static ActionManager getActionManager() {
        return actionManager;
    }

    public static KeyHandler getKeyHandler() {
        return keyHandler;
    }

    public static DebugDisplay getDebugDisplay() {
        return debugDisplay;
    }

}