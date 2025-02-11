package org.Game2D.engine.rendering;

import java.awt.*;

public class Texture {

    public int offsetX, offsetY, width, height;

    //Can be null
    public Image image;

    public Texture(int offsetX, int offsetY, int width, int height, Image image) {
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.width = width;
        this.height = height;
        this.image = image;
    }

}
