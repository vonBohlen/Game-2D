package org.Game2D.engine.physics;

public class Hitbox {

    // x, y -> center of object
    //width cords -> width % 2 != 0, x = width / 2 + 1 ->  width % 2 == 0, x = width / 2
    //height cords -> height % 2 != 0, y = height / 2 + 1 ->  height % 2 == 0, y = height / 2
    public int x, y, width, height;
    public boolean collision;
    public byte layer;
    public float mass;

    public Hitbox(int x, int y, int width, int height, boolean collision, byte layer, float mass) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.collision = collision;
        this.layer = layer;
        this.mass = mass;
    }

}
