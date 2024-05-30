package de.Game2D.engine.core;

import de.Game2D.engine.core.handlers.ConfHand;
import de.Game2D.engine.core.handlers.DataHand;
import de.Game2D.engine.core.managers.ActionMan;
import de.Game2D.engine.core.managers.RenderMan;

import de.Game2D.engine.core.handlers.Keyhand;

import javax.swing.*;
import java.nio.file.Path;

public class Instance {

    //Window
    JFrame window;

    //Gamelogicelements
    

    public Instance(Path confPath) {

        //Initialisatioin

        ConfHand.setConfPath(confPath);
        ConfHand.generateConf();

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
        window.setExtendedState(window.MAXIMIZED_BOTH);
        window.setUndecorated(true);
        window.setResizable(false);
        window.setTitle(windowTitle);

        window.add(DataHand.renderMan);

        window.pack();

        window.setLocationRelativeTo(null);
        window.setVisible(true);

    }

}
