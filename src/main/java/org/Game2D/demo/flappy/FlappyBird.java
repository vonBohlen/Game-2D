package org.Game2D.demo.flappy;

import org.Game2D.demo.flappy.entities.Bird;
import org.Game2D.demo.flappy.entities.InfoDisplay;
import org.Game2D.demo.flappy.entities.pipes.PipeHandler;
import org.Game2D.demo.flappy.entities.pipes.PipeManager;
import org.Game2D.demo.flappy.entities.pipes.PipePair;
import org.Game2D.demo.flappy.objects.BackgroundObject;
import org.Game2D.demo.flappy.objects.BaseObject;
import org.Game2D.engine.core.Instance;
import org.Game2D.engine.core.handlers.DataHand;
import org.Game2D.engine.objects.GameObject;
import org.Game2D.engine.utils.AssetMan;

import java.awt.*;

public class FlappyBird {

    public static InfoDisplay infoDisplay;

    public static void main(String[] args) {

        Instance instance = new Instance(null);
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

        //Init counter
        ScoreDisplay.start();

        int in = 1234567890;
        for (int i = in; i > 0;) {
            ScoreDisplay.upScore();
            i--;
        }

        for(GameObject go : DataHand.getGameObjs()){
            System.out.println(go.getClass().getName());
        }
    }

}
