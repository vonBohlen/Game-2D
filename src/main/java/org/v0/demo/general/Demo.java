package org.Game2D.v0.demo.general;

import org.Game2D.v0.demo.general.controlable.Physicsobject;
import org.Game2D.v0.demo.general.controlable.Player;
import org.Game2D.v0.demo.general.entities.Ball;
import org.Game2D.v0.demo.general.entities.BallwP;
import org.Game2D.v0.demo.general.entities.TpSquare;
import org.Game2D.v0.demo.general.objects.Square;
import org.Game2D.v0.engine.core.Instance;
import org.Game2D.v0.engine.core.handlers.DataHand;
import org.Game2D.v0.engine.core.managers.RenderMan;
import org.Game2D.v0.engine.objects.GameObject;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class Demo {

    public static void main(String[] args) {

        Instance instance = new Instance(null);
        instance.start("Demo");

        Image texture;

        try {
            texture = ImageIO.read(Objects.requireNonNull(RenderMan.class.getClassLoader().getResource("default.png")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        new Player(new Rectangle(200, 200, 48, 48), texture);

        new Square(new Rectangle(100, 52, 48, 48), texture);

        new BallwP(new Rectangle(600, 0, 48, 48), texture);

        new TpSquare(new Rectangle(800, 800, 48, 48), texture);

        new Ball(new Rectangle(0, 0, 48, 48), texture);

        new Physicsobject(true, texture, 60);

        for(GameObject go : DataHand.getGameObjs()){
            System.out.println(go.getClass().getName());
        }

    }

}
