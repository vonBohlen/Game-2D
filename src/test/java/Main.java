import de.Game2D.engine_old.core.Instance;
import de.Game2D.engine_old.objects.ObjectConfig;
import test_objs.*;

public class Main {

    public static void main(String[] args) {
        Instance instance = new Instance();
        instance.start("Test");

        ObjectConfig playerConfig = new ObjectConfig(instance);
        playerConfig.positionX = 200;
        playerConfig.positionY = 200;
        playerConfig.hitBoxWith = 48;
        playerConfig.hitBoxHeight = 48;
        playerConfig.positionLayer = 0;
        playerConfig.collision = true;
        new Player(playerConfig);

        ObjectConfig squareConfig = new ObjectConfig(instance);
        squareConfig.positionX = 400;
        squareConfig.positionY = 400;
        squareConfig.hitBoxWith = 48;
        squareConfig.hitBoxHeight = 48;
        squareConfig.positionLayer = 0;
        squareConfig.collision = true;
        new Square(squareConfig);

        ObjectConfig ballWpConfig = new ObjectConfig(instance);
        ballWpConfig.positionX = 600;
        ballWpConfig.positionY = 0;
        ballWpConfig.hitBoxWith = 48;
        ballWpConfig.hitBoxHeight = 48;
        ballWpConfig.positionLayer = 0;
        ballWpConfig.collision = true;
        new BallwP(ballWpConfig);

        ObjectConfig tpSquareConfig = new ObjectConfig(instance);
        tpSquareConfig.positionX = 800;
        tpSquareConfig.positionY = 800;
        tpSquareConfig.hitBoxWith = 48;
        tpSquareConfig.hitBoxHeight = 48;
        tpSquareConfig.positionLayer = 0;
        tpSquareConfig.collision = true;
        new TpSquare(tpSquareConfig);

        ObjectConfig ballConfig = new ObjectConfig(instance);
        ballConfig.positionX = 0;
        ballConfig.positionY = 0;
        ballConfig.hitBoxWith = 48;
        ballConfig.hitBoxHeight = 48;
        ballConfig.positionLayer = 0;
        ballConfig.collision = true;
        new Ball(ballConfig);

    }

}
