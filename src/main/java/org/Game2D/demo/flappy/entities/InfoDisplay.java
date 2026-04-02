/**
 * @author The Game2D contributors
 */

package org.Game2D.demo.flappy.entities;

import org.Game2D.engine.data.runtime.DataHand;
import org.Game2D.engine.data.disk.assets.AssetManager;
import org.Game2D.engine.graphics.Texture;
import org.Game2D.engine.objects.advanced.Entity;

import java.awt.*;

public class InfoDisplay extends Entity {

    boolean lastOver = true;
    int counter = 60;
    final Image messageTxt = AssetManager.getAsset("flappy_assets/ui/message.png");
    final Image gameoverTxt = AssetManager.getAsset("flappy_assets/ui/gameover.png");
    private final Texture texture;

    public InfoDisplay() {

        super(false, false, new Rectangle(DataHand.renderLoop.getWidth() / 2, DataHand.renderLoop.getHeight() / 2, 0, 0), 4);

        texture = new Texture(0, 0, messageTxt);
        addTexture("info_display", texture);

        register();
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

        if (texture.image == messageTxt) {
            posX -= 92;
            posY -= 133;
            hitbox.width = 184;
            hitbox.height = 267;
        }
        else if (texture.image == gameoverTxt) {
            posX -= 96;
            posY -= 21;
            hitbox.width = 192;
            hitbox.height = 42;
        }
        hitbox.x = posX;
        hitbox.y = posY;
    }

    public void showMessage() {
       //if(texture == null) return;
        texture.image = messageTxt;
        setPosition();
        renderEnabled = true;
    }

    public void showGameover() {
       // if(texture == null) return;
        texture.image = gameoverTxt;
        setPosition();
        renderEnabled = true;
    }

    public void clearDisplay() {
        renderEnabled = false;
    }
}
