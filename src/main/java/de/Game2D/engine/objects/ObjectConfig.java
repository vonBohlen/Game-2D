package de.Game2D.engine.objects;

import de.Game2D.engine.core.Instance;
import de.Game2D.engine.core.RenderManager;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class ObjectConfig {

    private final Instance instance;

    public int hitBoxWith = 48;
    public int hitBoxHeight = 48;

    public int positionX = 0;
    public int positionY = 0;

    public int positionLayer = 0;

    public boolean collision = false;

    public Image texture;

    {
        try {
            texture = ImageIO.read(Objects.requireNonNull(RenderManager.class.getClassLoader().getResource("default.png")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public ObjectConfig(Instance i) {
        instance = i;
    }

    public Instance getInstance() {
        return instance;
    }

}
