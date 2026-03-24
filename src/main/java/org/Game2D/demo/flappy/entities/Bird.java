/**
 * /demo/flappy/entities/Bird.java
 *
 * DESCRIPTION
 *
 * Copyright (C) 2026 Silas Vogel, Christian von Bohlen, J. K.
 */

package org.Game2D.demo.flappy.entities;

import org.Game2D.demo.flappy.FlappyBird;
import org.Game2D.engine.data.disk.assets.AssetManager;
import org.Game2D.engine.data.runtime.DataHand;
import org.Game2D.engine.graphics.Texture;
import org.Game2D.engine.objects.advanced.Entity;
import org.Game2D.engine.objects.loops.GameLoop;

import java.awt.*;

public class Bird extends Entity {

    double velo = 0.0; //only one velocity because the bird stays in position on x-axis
    final double veloOnPress = -10;
    final double gravityConst = 40; //earths gravity is 9.81
    double passedTime; //at 60 targetTPS it is around 1.6
    long lastTime;

    final Image txtMid = AssetManager.getAsset("flappy_assets/bird/yellowbird-midflap.png");
    final Image txtUp = AssetManager.getAsset("flappy_assets/bird/yellowbird-upflap.png");
    final Image txtDown = AssetManager.getAsset("flappy_assets/bird/yellowbird-downflap.png");

    private final Texture texture = new Texture(0, 0, txtMid);

    public static boolean gameOver = false;
    public static int speed = 10;

    //with more time the bird(the pipes) move faster but the movement speed is an int
    public static double remainder = 0.0;

    public Bird(Image txt) {

        //bird gets placed at one half of the height and one third of the width
        super(true, new Rectangle(DataHand.renderLoop.getWidth() / 5, DataHand.renderLoop.getHeight() / 2, 44, 24), 2);

        addTexture("bird", texture);

        //ideal time between two ticks
        this.passedTime = 1 / (double) GameLoop.targetTPS;

        lastTime = System.nanoTime();
    }

    private void updatePosition() {

        //if space bar is pressed the birds velocity is set to a fixed value
        if(DataHand.keyHand.keyPressed_SPACE){
            this.velo = this.veloOnPress;
        }

        //velocity towards the ground that the bird gains per tick
        //v(t) = a * t
        this.velo += this.gravityConst * this.passedTime;

        //checks if the top or bottom is reached
        if(this.hitbox.y <= 0 || this.hitbox.y + this.hitbox.getHeight() >= DataHand.renderLoop.getHeight() - 112){
            gameOver = true;
        }

        //converting the velocity into the needed datatype for the move method
        int moving = (int) this.velo;

        //setting the midflap texture
        if(this.velo <= 2 && this.velo >= -2){
            this.texture.image = this.txtMid;
        }
        else if(this.velo > 2){
            this.texture.image = this.txtUp;
        }
        else if(this.velo < -2){
            this.texture.image = this.txtDown;
        }

        if(!gameOver) {
            gameOver = move(0, moving) != null;
        }

        if(remainder >= 1){
            speed += (int) remainder;
            remainder--;
        }
    }

    @Override
    public void update() {
        if (DataHand.keyHand.keyPressed_ESC) FlappyBird.instance.exit();
        if(!gameOver) {
            updatePosition();
        }
        else if (DataHand.keyHand.keyPressed_SPACE){
            setDefault();
            speed = 10;
        }
    }

    public static int getBirdPosition(){
        return DataHand.renderLoop.getWidth() / 5;
    }

    @Override
    public void renderObject(Graphics2D g2) {
        g2.drawImage(texture.image, getScreenCoordinateX(-12), getScreenCoordinateY(-6), getCustomScreenSpace(64), getCustomScreenSpace(48), null);
    }

    private void setDefault(){
        setPosition(DataHand.renderLoop.getWidth() / 5, DataHand.renderLoop.getHeight() / 2);
    }
}
