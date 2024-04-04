package de.Game2D.engine.core;

import de.Game2D.engine.core.managers.ActionMan;
import de.Game2D.engine.core.managers.RenderMan;

import de.Game2D.engine.core.handlers.Keyhand;

import javax.swing.*;
import java.nio.file.Path;

public class Instance {

    public final Path path;

    //Manager
    public final ActionMan actMan;
    public final RenderMan rndMan;

    //Handlers
    public final Keyhand keyHandler;


    //Window
    JFrame window;

    //Gamelogicelements
    

    public Instance(Path confPath) {

        path = confPath;

        //Initialisatioin
        actMan = new ActionMan(this);

        rndMan = new RenderMan(this);

        keyHandler = new Keyhand();
    }

    public void Start(){

        //Loading Window
        LoadWindow();

        //Starting Managerloops

    }

    private void LoadWindow(){

    }

}
