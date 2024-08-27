package org.Game2D.demo.flappy;

import org.Game2D.engine.core.Instance;
import org.Game2D.engine.core.managers.RenderMan;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class FlappyBird {

    public static void main(String[] args) {

        Instance instance = new Instance(null);
        instance.start("Flappy bird");

        Image texture;

        try {
            texture = ImageIO.read(Objects.requireNonNull(RenderMan.class.getClassLoader().getResource("default.png")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }



    }

}
