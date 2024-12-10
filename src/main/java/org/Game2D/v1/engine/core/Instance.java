package org.Game2D.v1.engine.core;

import org.Game2D.v1.engine.core.handlers.ConfHand;
import org.Game2D.v1.engine.core.handlers.DataHand;
import org.Game2D.v1.engine.core.managers.ActionMan;
import org.Game2D.v1.engine.core.managers.RenderMan;

import org.Game2D.v1.engine.core.handlers.Keyhand;

import javax.swing.*;
import java.awt.*;
import java.nio.file.Path;

public class Instance {

    //Window
    JFrame window;

    //Gamelogicelements

    public Instance(Path confPath) {

        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("nix") || os.contains("nux") || os.contains("aix")) System.setProperty("sun.java2d.opengl", "true");
        System.out.println("OpenGL enabled: " + System.getProperty("sun.java2d.opengl") + " [Linux only]");

        //Initialisatioin

        ConfHand.setConfPath(confPath);
        ConfHand.generateConf();
        ConfHand.updateConf();

        DataHand.keyHand = new Keyhand();

        DataHand.renderMan = new RenderMan();

        DataHand.actionMan = new ActionMan();

    }

    public void start(String windowTitle){

        //Loading Window
        loadWindow(windowTitle);

        //Starting Managerloops
        DataHand.renderMan.startRenderLoop();

        DataHand.actionMan.startGameLoop();

    }

    private void loadWindow(String windowTitle){

        window = new JFrame();

        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setExtendedState(Frame.MAXIMIZED_BOTH);
        window.setUndecorated(true);
        window.setResizable(true);
        window.setTitle(windowTitle);

        window.add(DataHand.renderMan);

        window.pack();

        window.setLocationRelativeTo(null);
        window.setVisible(true);
        //window.setAlwaysOnTop(true);

    }

    public int exit() {
        DataHand.actionMan.exit();
        DataHand.renderMan.exit();

        window.dispose();

        return 0;
    }

}
