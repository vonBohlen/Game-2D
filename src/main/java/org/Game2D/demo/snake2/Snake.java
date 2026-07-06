package org.Game2D.demo.snake2;

import org.Game2D.engine.data.runtime.Instance;

public class Snake {


    private static Instance instance;

    public static void main(String[] args) {

        instance = new Instance();
        instance.start("Snake");

    }

}
