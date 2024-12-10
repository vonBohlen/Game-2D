package org.Game2D.v1.demo.pingPong;

import org.Game2D.v1.demo.pingPong.entities.Ball;
import org.Game2D.v1.demo.pingPong.entities.ComputerBar;
import org.Game2D.v1.demo.pingPong.entities.PlayerBar;
import org.Game2D.v1.engine.core.Instance;
import org.Game2D.v1.engine.core.handlers.DataHand;
import org.Game2D.v1.engine.utils.AssetMan;

import java.awt.*;

public class PingPong {

    public static int screenHeight;
    public static int screenWidth;

    static Image texture = AssetMan.loadAsset("default.png");

    public static void main(String[] args) {
        Instance instance = new Instance(null);
        instance.start("Ping pong");

        screenHeight = DataHand.renderMan.getHeight();
        screenWidth = DataHand.renderMan.getWidth();
        new Ball(false, texture);
        new PlayerBar(texture);
        new ComputerBar(texture);
    }
}
