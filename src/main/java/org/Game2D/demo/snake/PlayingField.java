package org.Game2D.demo.snake;

import java.awt.*;

public class PlayingField {
    private final PField[][] objects;
    public int size = 20;
    public int scale;
    private boolean landscape;
    private int screenX;
    private int screenY;

    /**
     * Construct a new square PlayingField with the specified size
     *
     * @param size Size of the PlayingField
     */
    public PlayingField(int size) {
        this.size = size;
        this.objects = new PField[this.size][this.size];
    }

    /**
     * Construct a new square PlayingField with size 20
     */
    public PlayingField() {
        this.objects = new PField[this.size][this.size];
    }

    /**
     * Sets the texture of a PField in the PlayingField to the specified Image
     *
     * @param posX x-Coordinate in the PlayingField
     * @param posY y-Coordinate in the PlayingField
     * @param img  Texture for the Field
     */
    public void setColor(int posX, int posY, Image img) {
        this.objects[posX][posY] = new PField(calcPFieldRect(posX, posY), false, 0, img);
    }

    /**
     * Calculate the Hitbox Rectangle for drawing the PField
     * Convert between PlayingField-Coordinates and global coordinates
     *
     * @param posX x-Coordinate in the PlayingField
     * @param posY y-Coordinate in the PlayingField
     * @return Hitbox Rectangle in the global coordinate-space
     */
    private Rectangle calcPFieldRect(int posX, int posY) {
        int absX = posX * this.scale / 2;
        int absY = posY * this.scale / 2;

        int offsetX = 0;
        int offsetY = 0;
        if (this.landscape) {
            offsetX = this.screenX / 4;
        } else {
            offsetY = this.screenY / 4;
        }
        return new Rectangle(absX + offsetX, absY + offsetY, 10, 10);
    }

    /**
     * Calculate the scaling of the PlayingField to maximise the used screen area
     * Also check if the screen is in landscape or portrait orientation
     *
     * @param screenWidth  Width of the screen in px
     * @param screenHeight Height of the screen in px
     */
    public void calcScalingFactor(int screenWidth, int screenHeight) {
        this.screenX = screenWidth;
        this.screenY = screenHeight;

        if (screenWidth > screenHeight) { // Landscape Screen
            this.landscape = true;
            this.scale = screenWidth / this.size;
        } else { // Portrait or Square Screen
            this.landscape = false;
            this.scale = screenHeight / this.size;
        }
    }

    /**
     * Print the current PlayingField to the console
     * Only needed for debug purposes
     */
    public void printPlayingField() {
        for (int i = 0; i < this.size; i++) {
            System.out.print("X = " + i + ":\n");
            for (int j = 0; j < this.size; j++) {
                System.out.print("Y = " + j + ": " + this.objects[i][j].toString());
            }
            System.out.print("\n");
        }
    }

    /**
     * Show the playing Field
     */
    public void render() {
        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {
                this.objects[i][j].show();
            }
        }
    }
}
