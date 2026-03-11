package org.Game2D.engine.graphics;

import lombok.NonNull;

import java.awt.*;

public class Texture {

    public int offsetX, offsetY;

    @NonNull
    public Image image;

    public Texture(int offsetX, int offsetY, @NonNull Image image) {
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.image = image;
    }

}
