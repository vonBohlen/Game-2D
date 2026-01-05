/**
 * @author The Game2D contributors
 */

package org.Game2D.demo.snake;

import org.Game2D.demo.snake.logic.Board;
import org.Game2D.demo.snake.logic.Cell;
import org.Game2D.demo.snake.logic.Game;
import org.Game2D.demo.snake.logic.Schnake;
import org.Game2D.engine.core.Instance;
import org.Game2D.engine.core.handlers.DataHand;
import org.Game2D.engine.utils.AssetMan;

import java.awt.*;

public class Main {
    private static int boardSize;

    public static void main(String[] args) {
        //Engine Boilerplate
        Instance instance = new Instance(null);
        instance.start("Snake");

        int screenWidth = DataHand.renderMan.getWidth();
        int screenHeight = DataHand.renderMan.getHeight();

        Image white = AssetMan.loadAsset("snake_assets/white10.png");
        Image black = AssetMan.loadAsset("snake_assets/black10.png");

        //Instantiate a new game
        Game game = new Game(new Schnake(generate_random_starting_pos()), new Board(boardSize, boardSize));

        renderBoard(game.getBoard());
    }

    private static Cell generate_random_starting_pos() {
        int posX = (int) (Math.random() * boardSize);
        int posY = (int) (Math.random() * boardSize);

        return new Cell(posX, posY);
    }

    private static void renderBoard(Board board) {
        for (Cell[] i : board.getCells()) {
            for (Cell j : i) {
            }
        }
    }
}
