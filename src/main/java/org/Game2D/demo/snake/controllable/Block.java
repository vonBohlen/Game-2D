package org.Game2D.demo.snake.controllable;

/**
 * Simple coordinate-based snake block<br>
 * Just stores the indices of the Block in the PlayingField
 * @see org.Game2D.demo.snake.PlayingField
 * @param posX x-Index
 * @param posY y-Index
 */
public record Block(int posX, int posY) {
}
