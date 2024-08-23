package org.Game2D.demo;

import org.Game2D.demo.entities.Ball;
import org.Game2D.demo.entities.BallwP;
import org.Game2D.demo.entities.TpSquare;
import org.Game2D.demo.objects.Physicsobject;
import org.Game2D.demo.objects.Square;
import org.Game2D.engine.core.Instance;
import org.Game2D.engine.core.managers.RenderMan;

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

        //new Player(new Rectangle(200, 200, 48, 48), texture);

        new Square(new Rectangle(100, 52, 48, 48), texture);

        //new BallwP(new Rectangle(600, 0, 48, 48), texture);

        //new TpSquare(new Rectangle(800, 800, 48, 48), texture);

        //new Ball(new Rectangle(0, 0, 48, 48), texture);

        new Physicsobject(true, texture, 60);

    }

}
