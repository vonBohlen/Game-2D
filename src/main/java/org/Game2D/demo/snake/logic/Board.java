package org.Game2D.demo.snake.logic;

public class Board {
    final int rows, cols;
    private Cell[][] cells;

    public Board(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;

        cells = new Cell[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                cells[i][j] = new Cell(i, j);
            }
        }
    }

    public Cell[][] getCells() {
        return cells;
    }

    public void setCells(Cell[][] cells) {
        this.cells = cells;
    }

    public void generate_food() {
        int row, column;
        while(true) {
            row = (int) (Math.random() * this.rows);
            column = (int) (Math.random() * this.cols);

            if (this.cells[row][column].getCellType() != CellType.SNAKE) {
                break;
            }

            cells[row][column].setCellType(CellType.FOOD);
        }
    }
}
