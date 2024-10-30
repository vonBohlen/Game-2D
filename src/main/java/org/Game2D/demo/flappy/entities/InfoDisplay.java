package org.Game2D.demo.flappy.entities;

import org.Game2D.engine.core.handlers.DataHand;
import org.Game2D.engine.core.managers.RenderMan;
import org.Game2D.engine.objects.advanced.Entity;
import org.Game2D.engine.utils.AssetMan;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class InfoDisplay extends Entity {

    Image messageTxt = AssetMan.loadAsset("flappy_assets/ui/message.png");
    Image gameoverTxt = AssetMan.loadAsset("flappy_assets/ui/gameover.png");

    public InfoDisplay() {

        super(new Rectangle(DataHand.renderMan.getWidth() / 2, DataHand.renderMan.getHeight() / 2, 0, 0), false, null);

    }

    @Override
    public void update() {}

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
    public void draw(Graphics2D g2) {
            g2.drawImage(texture, hitBox.x, hitBox.y, hitBox.width, hitBox.height, null);
    }

}
