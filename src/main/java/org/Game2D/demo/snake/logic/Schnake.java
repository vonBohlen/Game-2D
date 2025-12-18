package org.Game2D.demo.snake.logic;

import java.util.LinkedList;
import java.util.Queue;

public class Schnake {
    private Queue<Cell> snake_parts = new LinkedList<>();
    private Cell head;

    public Schnake(Cell starting_position) {
        this.head = starting_position;
        this.snake_parts.add(head);
        head.setCellType(CellType.SNAKE);
    }

    public void grow() {
        this.snake_parts.add(head);
    }

    public void move(Cell next_cell) {
        Cell tail = this.snake_parts.remove();
        tail.setCellType(CellType.EMPTY);

        this.head = next_cell;
        this.head.setCellType(CellType.SNAKE);
        this.snake_parts.add(head);
    }

    public boolean check_collision(Cell next_cell) {
        for (Cell i : this.snake_parts) {
            return i == next_cell;
        }
        return false;
    }

    public Queue<Cell> getSnake_parts() {
        return snake_parts;
    }

    public void setSnake_parts(Queue<Cell> snake_parts) {
        this.snake_parts = snake_parts;
    }

    public Cell getHead() {
        return head;
    }

    public void setHead(Cell head) {
        this.head = head;
    }
}
