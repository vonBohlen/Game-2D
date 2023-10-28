package de.Alienlive.games.Game2D.test;

import de.Alienlive.games.Game2D.core.ActionManager;
import de.Alienlive.games.Game2D.core.Instance;
import de.Alienlive.games.Game2D.core.KeyHandler;
import de.Alienlive.games.Game2D.core.RenderManager;
import de.Alienlive.games.Game2D.debug.DebugDisplay;
import de.Alienlive.games.Game2D.objects.entities.BallwP;
import de.Alienlive.games.Game2D.objects.entities.Player;
import de.Alienlive.games.Game2D.objects.entities.Square;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        Instance instance = new Instance();
        instance.start("Test");

        new Player(200, 200, 48, 48, instance);
        new Square(400, 400, 48, 48, instance);
        new BallwP(0, 0, 48, 48, instance);
    }

}