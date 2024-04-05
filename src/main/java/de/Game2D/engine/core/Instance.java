package de.Game2D.engine.core;

import de.Game2D.engine.core.handlers.ConfHand;
import de.Game2D.engine.core.handlers.DataHand;
import de.Game2D.engine.core.managers.ActionMan;
import de.Game2D.engine.core.managers.RenderMan;

import de.Game2D.engine.core.handlers.Keyhand;
import de.Game2D.engine.utils.ConfProvider;

import javax.swing.*;
import java.io.File;
import java.nio.file.Path;
import java.util.Properties;

public class Instance {

    //Window
    JFrame window;

    //Gamelogicelements
    

    public Instance(Path confPath) {

        //Initialisatioin

        ConfHand.setConfPath(confPath);
        ConfHand.generateConf();

        DataHand.renderMan = new RenderMan(this);

        DataHand.keyHand = new Keyhand();

        DataHand.actionMan = new ActionMan(this);

    }

    public void start(String windowTitle){

        //Loading Window
        loadWindow(windowTitle);

        //Starting Managerloops

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
