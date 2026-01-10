/**
 * @author The Game2D contributors
 */

package org.Game2D.demo.flappy.entities;

import org.Game2D.demo.flappy.FlappyBird;
import org.Game2D.engine.data.runtime.DataHand;
import org.Game2D.engine.objects.advanced.Entity;
import org.Game2D.engine.io.assets.AssetMan;
import org.Game2D.engine.io.conf.ConfProvider;

import java.awt.*;

public class Bird extends Entity {

    double velo = 0.0; //only one velocity because the bird stays in position on x-axis
    final double veloOnPress = -10;
    final double gravityConst = 40; //earths gravity is 9.81
    double passedTime; //at 60 targetTPS it is around 1.6
    long lastTime;


    final Image txtMid = AssetMan.loadAsset("flappy_assets/bird/yellowbird-midflap.png");
    final Image txtUp = AssetMan.loadAsset("flappy_assets/bird/yellowbird-upflap.png");
    final Image txtDown = AssetMan.loadAsset("flappy_assets/bird/yellowbird-downflap.png");

    public static boolean gameOver = false;
    public static int speed = 10;

    //with more time the bird(the pipes) move faster but the movement speed is an int
    public static double remainder = 0.0;

    public Bird(Image txt) {

        //bird gets placed at one half of the height and one third of the width
        super(new Rectangle(DataHand.renderLoop.getWidth() / 5, DataHand.renderLoop.getHeight() / 2, 44, 24), true, 2, txt);

        //ideal time between two ticks
        this.passedTime = 1 / (double) Integer.parseInt(ConfProvider.getConf(DataHand.confPath).getProperty("game2d.core.tps"));

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
        if(this.hitBox.y <= 0 || this.hitBox.y + this.hitBox.getHeight() >= DataHand.renderLoop.getHeight() - 112){
            gameOver = true;
        }

        //converting the velocity into the needed datatype for the move method
        int moving = (int) this.velo;

        //setting the midflap texture
        if(this.velo <= 2 && this.velo >= -2){
            this.texture = this.txtMid;
        }
        else if(this.velo > 2){
            this.texture = this.txtUp;
        }
        else if(this.velo < -2){
            this.texture = this.txtDown;
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
    public void render(Graphics2D g2) {
        g2.drawImage(texture, getScreenCoordinateX(-12), getScreenCoordinateY(-6), getCustomScreenSpace(64), getCustomScreenSpace(48), null);
    }

    private void setDefault(){
        setPosition(DataHand.renderLoop.getWidth() / 5, DataHand.renderLoop.getHeight() / 2);
    }
}
