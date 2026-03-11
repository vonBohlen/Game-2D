package org.Game2D.demo.snake2.board;

import lombok.NonNull;
import org.Game2D.engine.objects.advanced.StaticObject;

import java.awt.*;

public class Cell extends StaticObject {

    public Cell(@NonNull Rectangle hitbox, int objectLayer, @NonNull Image texture) {
        super(false, hitbox, objectLayer);
    }

}
