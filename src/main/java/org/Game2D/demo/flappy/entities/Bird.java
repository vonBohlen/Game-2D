package org.Game2D.demo.flappy.entities;

import org.Game2D.engine.core.handlers.DataHand;
import org.Game2D.engine.core.managers.RenderMan;
import org.Game2D.engine.objects.advanced.Entity;
import org.Game2D.engine.utils.ConfProvider;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class Bird extends Entity {

    double velo = 0.0; //only one velocity because the bird stays in position on x-axis
    double veloOnPress = -7.5;
    double gravityConst = 9.81; //earths gravity is 9.81

    double passedTime; //at 60 tps it is around 1.6

    Image txtMid;
    Image txtUp;
    Image txtDown;

    boolean gameOver = false;

    public Bird(Image txt) {

        //bird gets placed at one half of the height and one third of the width
        super(new Rectangle(DataHand.renderMan.getWidth() / 5, DataHand.renderMan.getHeight() / 2, 68, 48), true, txt);

        try {
            txtMid = ImageIO.read(Objects.requireNonNull(RenderMan.class.getClassLoader().getResource("flappy_assets/bird/yellowbird-midflap.png")));
            txtUp = ImageIO.read(Objects.requireNonNull(RenderMan.class.getClassLoader().getResource("flappy_assets/bird/yellowbird-upflap.png")));
            txtDown = ImageIO.read(Objects.requireNonNull(RenderMan.class.getClassLoader().getResource("flappy_assets/bird/yellowbird-downflap.png")));
        }
        catch (IOException e){
            throw new RuntimeException();
        }

        //die Zeit die idealerweise zwischen zwei ticks vergeht
        this.passedTime = 1 / (double) Integer.parseInt(ConfProvider.getConf(DataHand.confPath).getProperty("game2d.core.tps"));
    }

    private void updatePosition() {

        //if space bar is pressed the birds velocity is set to a fixed value
        if(DataHand.keyHand.keyPressed_SPACE){
            this.velo = this.veloOnPress;
        }

        //checks if the top or bottom is reached
        if(this.hitBox.y <= 0 || this.hitBox.y + this.hitBox.getHeight() >= DataHand.renderMan.getHeight() - 112){
            this.gameOver = true;
        }

        //calculating the speed that is added after the passed time
        this.velo += this.gravityConst * this.passedTime;

        //converting the velocity into the needed datatype for the move method
        int moving = (int) this.velo;

        //setting the midflap texture
        if(this.velo <= 2 && this.velo >= -2){
            this.texture = this.txtMid;
        }

        if(this.velo > 2){
            this.texture = this.txtUp;
        }

        if(this.velo < -2){
            this.texture = this.txtDown;
        }

        //if null is returned the bird moved without touching an object
        //the if branche is to determine whether or whether game over was true before
        if(!this.gameOver) {
            this.gameOver = move(0, moving) != null;
        }
    }

    @Override
    public void update() {
        if(!this.gameOver) {
            updatePosition();
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        g2.drawImage(texture, hitBox.x, hitBox.y, hitBox.width, hitBox.height, null);
    }
}
