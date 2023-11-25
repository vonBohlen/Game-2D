import de.Game2D.engine.core.Instance;
import custom.BallwP;
import custom.Player;
import custom.Square;
import custom.TpSquare;

public class Main {

    public static void main(String[] args) {
        Instance instance = new Instance();
        instance.start("Test");

        new Player(200, 200, 48, 48, instance);
        new Square(400, 400, 48, 48, instance);
        new BallwP(0, 0, 48, 48, instance);
        new TpSquare(800, 800, 48, 48, instance);

    }

}