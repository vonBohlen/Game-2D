import custom.*;
import de.Game2D.engine.core.Instance;

public class Main {

    public static void main(String[] args) {
        Instance instance = new Instance();
        instance.start("Test");

        new Player(200, 200, 48, 48, instance);
        new Square(400, 400, 48, 48, instance);
        new BallwP(600, 0, 48, 48, instance);
        new TpSquare(800, 800, 48, 48, instance);
        new Ball(0, 0, 48, 48, instance);

    }

}