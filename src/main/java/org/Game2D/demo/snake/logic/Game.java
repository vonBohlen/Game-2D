package org.Game2D.demo.snake.logic;

public class Game {
    private Direction direction;
    private Schnake snake;
    private Board board;
    private boolean game_running;

    public Game(Schnake snake, Board board) {
        this.snake = snake;
        this.board = board;
    }

    public Schnake getSnake() {
        return snake;
    }

    public void setSnake(Schnake snake) {
        this.snake = snake;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public boolean isGame_running() {
        return game_running;
    }

    public void setGame_running(boolean game_running) {
        this.game_running = game_running;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public Cell getNextCell(Cell head) {
        int row = head.getPosX();
        int col = head.getPosY();

        switch (this.direction) {
            case UP -> row++;
            case DOWN -> row--;
            case RIGHT -> col++;
            case LEFT -> col--;
        }

        return board.getCells()[row][col];
    }

    public void update() {
        if (!game_running) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.exit(0);
        }

        Cell next_cell = this.getNextCell(this.snake.getHead());
        if(this.snake.check_collision(next_cell)) {
            game_running = false;
        } else {
            snake.move(next_cell);
            if(next_cell.getCellType() == CellType.FOOD) {
                this.snake.grow();
                this.board.generate_food();
            }
        }
    }
}
