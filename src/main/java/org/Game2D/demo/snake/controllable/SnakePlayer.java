package org.Game2D.demo.snake.controllable;

import org.Game2D.engine.core.handlers.DataHand;
import org.Game2D.engine.objects.advanced.Entity;

import java.awt.*;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Control structure for the snake demo
 */
public class SnakePlayer extends Entity {
    /**
     * List of snake "Blocks"<br>
     * Implemented as Queue to easily facilitate moving the tail
     * of the snake to the Front
     */
    private static final Queue<Block> blocks = new LinkedList<>();

    public static int posX;
    public static int velocityX;
    public static int posY;
    public static int velocityY;
    private static int sidelength;

    public SnakePlayer(boolean collision, int objectLayer, Image txt, int fieldSize) {
        super(chooseStartingPosition(), collision, objectLayer, txt);
        sidelength = fieldSize;
    }

    /**
     * Randomly generate a starting position for the snake<br>
     * Also generate the head of the snake at that position
     * @return Hitbox object
     */
    private static Rectangle chooseStartingPosition() {
        int startX = (int) (Math.random() * 20 - 1);
        int startY = (int) (Math.random() * 20 - 1);

        posX = startX;
        posY = startY;
        Block starting_block = new Block(startX, startY);
        blocks.add(starting_block);

        return new Rectangle(startX * sidelength / 20, startY * sidelength / 20, 30, 30);
    }

    /**
     * Move the snake head to the right by moving the tail of the snake
     * to the front
     */
    private void move_right() {
        Block last_block = blocks.remove();

        int newX = posX + 1;
        posX = newX;
        if (newX > sidelength) {
            gameOver();
        }
        Block new_block = new Block(newX, last_block.posY());

        blocks.add(new_block);
    }

    /**
     * Move the snake head to the left by moving the tail of the snake
     * to the front
     */
    private void move_left() {
        Block last_block = blocks.remove();

        int newX = posX - 1;
        posX = newX;
        if (newX < sidelength) {
            gameOver();
        }
        Block new_block = new Block(newX, last_block.posY());

        blocks.add(new_block);
    }

    /**
     * Move the snake head up by moving the tail of the snake
     * to the front
     */
    private void move_up() {
        Block last_block = blocks.remove();

        int newY = posY + 1;
        posY = newY;
        if (newY > sidelength) {
            gameOver();
        }
        Block new_block = new Block(last_block.posX(), newY);

        blocks.add(new_block);
    }

    /**
     * Move the snake head down by moving the tail of the snake
     * to the front
     */
    private void move_down() {
        Block last_block = blocks.remove();

        int newY = posY - 1;
        posY = newY;
        if (newY > sidelength) {
            gameOver();
        }
        Block new_block = new Block(last_block.posX(), newY);

        blocks.add(new_block);
    }

    /**
     * Exit the program on game over<br>
     * Wait one second before actually exiting to allow the player
     * to look at the game one last time
     */
    private void gameOver() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.exit(0);
    }

    /**
     * Move the snake in the direction specified by the player<br>
     * Keeps the current direction if no new key is pressed<br>
     * Periodically called by the Gameloop of the ActionManager
     */
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
