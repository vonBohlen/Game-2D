package org.Game2D.engine.core;

import lombok.Getter;
import lombok.Setter;
import org.Game2D.engine.chunks.Chunk;
import org.Game2D.engine.chunks.ChunkMan;
import org.Game2D.engine.core.handlers.ConfHand;
import org.Game2D.engine.core.handlers.DataHand;
import org.Game2D.engine.core.handlers.Keyhand;
import org.Game2D.engine.core.managers.ActionMan;
import org.Game2D.engine.core.managers.RenderMan;

import javax.swing.*;
import java.awt.*;
import java.nio.file.Path;

public class Instance {

    //Window
    private JFrame window;

    //Engine values
    @Getter
    @Setter
    private int updateDistance = 16; // the amount of chunks to be updated in each direction from the start chunk

    @Getter
    @Setter
    private int renderDistance = 16; // the amount of chunks to be rendered in each direction from the start chunk

    @Getter
    @Setter
    private int chunkSize = 512; // each chunk is a square with a sidelength of chunk size

    //Game Logic Elements

    /**
     * Implements basic Window Logic
     * Hardware acceleration on Wayland with NVIDIA Graphics is
     * not working properly at this time
     * To use hardware acceleration, uncomment the given section in
     * the class constructor
     *
     * @param confPath Path to the config file
     *                 Defaults to ./config.properties
     */
    public Instance(Path confPath) {

        //IMPORTANT
        //Hardware acceleration is known to cause problems on wayland while using NVIDIA graphic cards

        //String os = System.getProperty("os.name").toLowerCase();
        //if (os.contains("nix") || os.contains("nux") || os.contains("aix")) System.setProperty("sun.java2d.opengl", "true");
        //System.out.println("[Linux only]" + " OpenGL enabled: " + System.getProperty("sun.java2d.opengl"));

        //Initialization

        ConfHand.setConfPath(confPath);
        ConfHand.generateConf();
        ConfHand.updateConf();

        DataHand.keyHand = new Keyhand();

        DataHand.renderMan = new RenderMan();

        DataHand.actionMan = new ActionMan();

        DataHand.renderMan.initializeCamera();
    }

    /**
     * Create a new window
     *
     * @param windowTitle Window title
     */
    public void start(String windowTitle) {

        //Loading Window
        loadWindow(windowTitle);

        //Create a single Chunk, otherwise no other Chunks can be added
        ChunkMan.addChunk(new Chunk(0, 0));

        //Starting ManagerLoops
        DataHand.renderMan.startRenderLoop();

        DataHand.actionMan.startGameLoop();

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

        window.add(DataHand.renderMan);

        window.pack();

        window.setLocationRelativeTo(null);
        window.setVisible(true);
        //window.setAlwaysOnTop(true);

    }

    /**
     * Spin down all Managers and close the window
     */
    public void exit() {
        DataHand.actionMan.exit();
        DataHand.renderMan.exit();

        window.dispose();
    }

}
