package org.Game2D.demo.Snake;

import org.Game2D.demo.Snake.controllable.SnakePlayer;
import org.Game2D.engine.core.Instance;
import org.Game2D.engine.core.handlers.DataHand;
import org.Game2D.engine.utils.AssetMan;

import java.awt.*;

public class Snake {
    public static Instance instance;
    public static PlayingField playingField;

    public static void main(String[] args) {
        instance = new Instance(null);
        instance.start("Snake");

        int screenWidth = DataHand.renderMan.getWidth();
        int screenHeight = DataHand.renderMan.getHeight();

        Image default_texture = AssetMan.loadAsset("default.png");
        Image white = AssetMan.loadAsset("snake_assets/white10.png");
        Image black = AssetMan.loadAsset("snake_assets/black10.png");

        playingField = new PlayingField(20);
        playingField.calcScalingFactor(screenWidth, screenHeight);

        for (int i = 0; i < playingField.size; i++) {
            for (int j = 0; j < playingField.size; j++) {
                playingField.setColor(i, j, white);
            }
        }
        playingField.printPlayingField();
        playingField.render();

        //SnakePlayer snake = new SnakePlayer(false, 0, white, playingField.size);
    }
}
