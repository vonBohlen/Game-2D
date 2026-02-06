package org.Game2D.demo.snake2.snake;

import lombok.NonNull;
import org.Game2D.engine.objects.advanced.Entity;

import java.awt.*;

public class SnakeBody extends Entity {

    public SnakeBody(boolean collisionEnabled, @NonNull Rectangle hitbox, int objectLayer, @NonNull Image texture) {
        super(collisionEnabled, hitbox, objectLayer, texture);
    }

    @Override
    public void update() {

    }
}
