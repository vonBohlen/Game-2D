package org.Game2D.demo.snake2.snake;

import lombok.NonNull;
import org.Game2D.engine.objects.advanced.Entity;

import java.awt.*;

public class SnakeHead extends Entity {

    public SnakeHead(boolean collisionEnabled, @NonNull Rectangle hitbox, int objectLayer, @NonNull Image texture) {
        super(collisionEnabled, hitbox, objectLayer);
    }

    @Override
    public void update() {

    }

}
