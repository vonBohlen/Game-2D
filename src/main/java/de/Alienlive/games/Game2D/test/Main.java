package de.Alienlive.games.Game2D.test;

import de.Alienlive.games.Game2D.core.Instance;
import de.Alienlive.games.Game2D.objects.entities.BallwP;
import de.Alienlive.games.Game2D.objects.entities.Player;
import de.Alienlive.games.Game2D.objects.entities.Square;
import de.Alienlive.games.Game2D.objects.entities.TpSquare;
import de.Alienlive.games.Game2D.objects.objects.Border;
import de.Alienlive.games.Game2D.objects.objects.TestObject;
import de.Alienlive.games.Game2D.objects.objects.object.Cavity;

import javax.swing.*;
import java.lang.reflect.Array;

public class Main {

    public static void main(String[] args) {
        Instance instance = new Instance();
        instance.start("Test");

        new Player(200, 200, 48, 48, instance);
        new Square(400, 400, 48, 48, instance);
        new BallwP(0, 0, 48, 48, instance);
        new TpSquare(800, 800, 48, 48, instance);

        Cavity[] testCavities = new Cavity[0];
        new TestObject(600, 600, 48, 200, testCavities, instance);

        /*Cavity[] borderCavities = new Cavity[1];
        borderCavities[0] = new Cavity(0, 0 , instance.getHeight(), instance.getWidth());
        new Border(-1, -1, instance.getHeight() + 1, instance.getWidth() + 1, borderCavities, instance);*/

    }

}