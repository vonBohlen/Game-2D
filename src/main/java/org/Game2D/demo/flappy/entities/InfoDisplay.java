/**
 * @author The Game2D contributors
 */

package org.Game2D.demo.flappy.entities;

import org.Game2D.engine.data.runtime.DataHand;
import org.Game2D.engine.data.disk.assets.AssetMan;
import org.Game2D.engine.objects.advanced.Entity;

import java.awt.*;

public class InfoDisplay extends Entity {

    boolean lastOver = true;
    int counter = 60;
    final Image messageTxt = AssetMan.loadAsset("flappy_assets/ui/message.png");
    final Image gameoverTxt = AssetMan.loadAsset("flappy_assets/ui/gameover.png");

    public InfoDisplay() {

        super(false, false, new Rectangle(DataHand.renderLoop.getWidth() / 2, DataHand.renderLoop.getHeight() / 2, 0, 0), 4);

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
        int posX = DataHand.renderLoop.getWidth() / 2;
        int posY = DataHand.renderLoop.getHeight() / 2;

        if (texture == messageTxt) {
            posX -= 92;
            posY -= 133;
            hitbox.width = 184;
            hitbox.height = 267;
        }
        else if (texture == gameoverTxt) {
            posX -= 96;
            posY -= 21;
            hitbox.width = 192;
            hitbox.height = 42;
        }
        hitbox.x = posX;
        hitbox.y = posY;
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
}
