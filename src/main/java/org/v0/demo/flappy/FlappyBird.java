package org.Game2D.v0.demo.flappy;

import org.Game2D.v0.demo.flappy.entities.Bird;
import org.Game2D.v0.demo.flappy.entities.InfoDisplay;
import org.Game2D.v0.demo.flappy.entities.pipes.PipeHandler;
import org.Game2D.v0.demo.flappy.objects.BackgroundObject;
import org.Game2D.v0.demo.flappy.objects.BaseObject;
import org.Game2D.v0.engine.core.Instance;
import org.Game2D.v0.engine.core.handlers.DataHand;
import org.Game2D.v0.engine.utils.AssetMan;

import java.awt.*;

public class FlappyBird {

    public static Instance instance;

    public static InfoDisplay infoDisplay;

    public static void main(String[] args) {

        instance = new Instance(null);
        instance.start("Flappy bird");

        int screenHeight = DataHand.renderMan.getHeight();
        int screenWidth = DataHand.renderMan.getWidth();

        Image texture = AssetMan.loadAsset("default.png");
        Image blueTxt = AssetMan.loadAsset("flappy_assets/background/blue.png");
        Image backgroundTxt = AssetMan.loadAsset("flappy_assets/background/background-day.png");
        Image baseTxt = AssetMan.loadAsset("flappy_assets/background/base.png");

        //Init background
        for (int i = 0; i <= screenWidth / 288; i++) {
            new BackgroundObject(new Rectangle(i * 288, 0, 288, screenHeight - 122 - 500), false, blueTxt);
            new BackgroundObject(new Rectangle(i * 288, screenHeight - 122 - 500, 288, 512), false, backgroundTxt);
        }


        //Init bird
        new Bird(texture);

        //Init pipes
        new PipeHandler();

        //Init base
        for (int i = 0; i <= screenWidth/336; i++) {
            new BaseObject(new Rectangle(i * 336, screenHeight - 112, 336, 112), false, baseTxt);
        }

        //Init InfoDisplay
        infoDisplay = new InfoDisplay();
        infoDisplay.showMessage();

        //Init ScoreDisplay
        ScoreDisplay.init();

    }

}
