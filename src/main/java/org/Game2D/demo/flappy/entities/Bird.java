package org.Game2D.demo.flappy.entities;

import org.Game2D.demo.flappy.FlappyBird;
import org.Game2D.engine.core.handlers.DataHand;
import org.Game2D.engine.core.managers.RenderMan;
import org.Game2D.engine.objects.advanced.Entity;
import org.Game2D.engine.utils.AssetMan;
import org.Game2D.engine.utils.ConfProvider;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class Bird extends Entity {

    double velo = 0.0; //only one velocity because the bird stays in position on x-axis
    double veloOnPress = -10;
    double gravityConst = 40; //earths gravity is 9.81
    double passedTime; //at 60 tps it is around 1.6


    Image txtMid = AssetMan.loadAsset("flappy_assets/bird/yellowbird-midflap.png");
    Image txtUp = AssetMan.loadAsset("flappy_assets/bird/yellowbird-upflap.png");
    Image txtDown = AssetMan.loadAsset("flappy_assets/bird/yellowbird-downflap.png");

    public static boolean gameOver = false;
    public static int speed = 10;

    //with more time the bird(the pipes) move faster but the movement speed is a int
    public static double remainder = 0.0;

    public Bird(Image txt) {

        //bird gets placed at one half of the height and one third of the width
        super(new Rectangle(DataHand.renderMan.getWidth() / 5, DataHand.renderMan.getHeight() / 2, 68, 48), true, txt);

        //ideal time between two ticks
        this.passedTime = 1 / (double) Integer.parseInt(ConfProvider.getConf(DataHand.confPath).getProperty("game2d.core.tps"));
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
        if(this.hitBox.y <= 0 || this.hitBox.y + this.hitBox.getHeight() >= DataHand.renderMan.getHeight() - 112){
            this.gameOver = true;
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
            this.gameOver = move(0, moving) != null;
        }

        if(remainder >= 1){
            speed += remainder;
            remainder--;
            System.out.println(speed);
        }
    }

    @Override
    public void update() {
        if (DataHand.keyHand.keyPressed_ESC) FlappyBird.instance.exit();
        if(!this.gameOver) {
            updatePosition();
        }
        else if (gameOver && DataHand.keyHand.keyPressed_SPACE){
            setDefault();
            speed = 10;
        }
    }

    public static int getBirdPosition(){
        return DataHand.renderMan.getWidth() / 5;
    }

    @Override
    public void draw(Graphics2D g2) {
        g2.drawImage(texture, hitBox.x, hitBox.y, hitBox.width, hitBox.height, null);
    }

    private void setDefault(){
        setPosition(DataHand.renderMan.getWidth() / 5, DataHand.renderMan.getHeight() / 2);
    }
}
