package de.Alienlive.games.Game2D.objects.objects;

import de.Alienlive.games.Game2D.core.Main;
import de.Alienlive.games.Game2D.objects.Direction;

public class Object {

    private int positionX;
    private int positionY;

    public Direction facing;

    public Object(int x, int y, Direction facing) {

        //TODO: logic for collision
        this.positionX = x;
        this.positionY = y;
        this.facing = facing;


    }

    public void changePosition(int x, int y) {
        this.positionX = x;
        this.positionY = y;
    }

    public int getPositionX() {
        return positionX;
    }

    public int getPositionY() {
        return positionY;
    }
}
