package org.Game2D.v0.demo.flappy.entities;

import org.Game2D.v0.engine.core.handlers.DataHand;
import org.Game2D.v0.engine.objects.advanced.Entity;
import org.Game2D.v0.engine.utils.AssetMan;

import java.awt.*;

public class InfoDisplay extends Entity {

    boolean lastOver = true;
    int counter = 60;
    Image messageTxt = AssetMan.loadAsset("flappy_assets/ui/message.png");
    Image gameoverTxt = AssetMan.loadAsset("flappy_assets/ui/gameover.png");

    public InfoDisplay() {

        super(new Rectangle(DataHand.renderMan.getWidth() / 2, DataHand.renderMan.getHeight() / 2, 0, 0), false, 4, null);

    }

    @Override
    public void update() {
        if(lastOver && !Bird.gameOver){
            lastOver = false;
            counter = 60;
        }
        if(Bird.gameOver){
            lastOver = true;
            showGameover();
        }
        else if(counter > 0){
            counter--;
            showMessage();
        }
        else{
            clearDisplay();
        }
    }

    private void setPosition() {
        int posX = DataHand.renderMan.getWidth() / 2;
        int posY = DataHand.renderMan.getHeight() / 2;

        if (texture == messageTxt) {
            posX -= 92;
            posY -= 133;
            hitBox.width = 184;
            hitBox.height = 267;
        }
        else if (texture == gameoverTxt) {
            posX -= 96;
            posY -= 21;
            hitBox.width = 192;
            hitBox.height = 42;
        }
        hitBox.x = posX;
        hitBox.y = posY;
    }

    public void showMessage() {
        texture = messageTxt;
        setPosition();
    }

    public void showGameover() {
        texture = gameoverTxt;
        setPosition();
    }

    public void clearDisplay() {
        texture = null;
    }

    @Override
    public void render(Graphics2D g2) {
            g2.drawImage(texture, hitBox.x, hitBox.y, hitBox.width, hitBox.height, null);
    }

}
