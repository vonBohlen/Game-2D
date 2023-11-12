package de.Game2D.engine.test;

import de.Game2D.engine.core.Instance;
import de.Game2D.engine.custom.BallwP;
import de.Game2D.engine.custom.Player;
import de.Game2D.engine.custom.Square;
import de.Game2D.engine.custom.TpSquare;
import de.Game2D.engine.old.objects.TestObject;
import de.Game2D.engine.old.objects.object.Cavity;

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