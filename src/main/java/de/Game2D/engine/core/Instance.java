package de.Game2D.engine.core;

import de.Game2D.engine.core.managers.ActMan;
import de.Game2D.engine.core.managers.RndMan;
import de.Game2D.engine.utils.Keyhand;
import de.Game2D.engine.utils.PrtProv;

import javax.swing.*;

public class Instance {

    //Manager
    private final ActMan actMan;
    private final RndMan rndMan;

    //Utils
    private final Keyhand keyHandler;
    private final PrtProv PrtManager;

    //Window
    JFrame window;

    //Gamelogicelements
    

    public Instance(){

        //Initialisatioin
        actMan = new ActMan(this);
        rndMan = new RndMan(this);

        keyHandler = new Keyhand();
        PrtManager = new PrtProv();
    }

    public void Start(){

        //Loading Window
        LoadWindow();

        //Starting Managerloops
    }

    private void LoadWindow(){

    }
}
