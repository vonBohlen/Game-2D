package org.Game2D.demo.Snake.controllable;

import org.Game2D.engine.core.handlers.DataHand;
import org.Game2D.engine.objects.advanced.Entity;

import java.awt.*;
import java.util.LinkedList;
import java.util.Queue;

public class SnakePlayer extends Entity {
    public static int posX;
    public static int velocityX;
    public static int posY;
    public static int velocityY;

    private static final Queue<Block> blocks = new LinkedList<>();

    private static int sidelength;

    public SnakePlayer(boolean collision, int objectLayer, Image txt, int fieldSize) {
        super(chooseStartingPosition(), collision, objectLayer, txt);
        sidelength = fieldSize;
    }

    private static Rectangle chooseStartingPosition() {
        int startX = (int) (Math.random() * 20 - 1);
        int startY = (int) (Math.random() * 20 - 1);

        posX = startX;
        posY = startY;
        Block starting_block = new Block(startX, startY);
        blocks.add(starting_block);

        return new Rectangle(startX, startY, 10, 10);
    }

    private void move_right() {
        Block last_block = blocks.remove();

        int newX = posX + 1;
        posX = newX;
        if (newX > sidelength) {
            gameOver();
        }
        Block new_block = new Block(newX, last_block.posY);

        blocks.add(new_block);
    }

    private void move_left() {
        Block last_block = blocks.remove();

        int newX = posX - 1;
        posX = newX;
        if (newX < sidelength) {
            gameOver();
        }
        Block new_block = new Block(newX, last_block.posY);

        blocks.add(new_block);
    }

    private void move_up() {
        Block last_block = blocks.remove();

        int newY = posY + 1;
        posY = newY;
        if (newY > sidelength) {
            gameOver();
        }
        Block new_block = new Block(last_block.posX, newY);

        blocks.add(new_block);
    }

    private void move_down() {
        Block last_block = blocks.remove();

        int newY = posY - 1;
        posY = newY;
        if (newY > sidelength) {
            gameOver();
        }
        Block new_block = new Block(last_block.posX, newY);

        blocks.add(new_block);
    }

    private void gameOver() {
        try {Thread.sleep(1000);} catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.exit(0);
    }

    @Override
    public void update() {
        if (DataHand.keyHand.keyPressed_A) {
            velocityX = -1;
            velocityY = 0;
        } else if (DataHand.keyHand.keyPressed_D) {
            velocityX = 1;
            velocityY = 0;
        } else if (DataHand.keyHand.keyPressed_S) {
            velocityX = 0;
            velocityY = 1;
        } else if (DataHand.keyHand.keyPressed_W) {
            velocityX = 0;
            velocityY = -1;
        } else if (DataHand.keyHand.keyPressed_ESC) {
            System.exit(0);
        }

        switch (velocityX) {
            case 1:
                move_right();
                break;
            case -1:
                move_left();
                break;
        }
        switch (velocityY) {
            case 1:
                move_down();
                break;
            case -1:
                move_up();
                break;
        }
    }

    @Override
    public void render(Graphics2D g2) {

    }
}
