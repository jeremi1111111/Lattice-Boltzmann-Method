package com.lbm;

import javafx.scene.canvas.GraphicsContext;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Board {
    private final double cellSize;
    private final int height;
    private final int width;
    private int currentMaxIteration = 0;
    private final CellInterface[][] board;

    public Board(double[][] stateMatrix, int cellSize) {
        this.cellSize = cellSize;
        this.height = stateMatrix.length;
        this.width = stateMatrix[0].length;

        this.board = new CellInterface[height][width];
        for (int y = 0; y < height; y++)
            for (int x = 0; x < width; x++)
                board[y][x] = (stateMatrix[y][x] >= 0. ?
                        new Cell(x, y, cellSize * x, cellSize * y, stateMatrix[y][x], this) :
                        new Border(x, y, cellSize * x, cellSize * y, this));
    }

    private void drawCell(GraphicsContext gc, CellInterface cell) {
        gc.setFill(cell.getColor());
        gc.fillRect(cell.getCanvasX(), cell.getCanvasY(), cellSize, cellSize);
    }

    public void drawCanvas(GraphicsContext gc) {
        for (CellInterface[] row : board)
            for (CellInterface cell : row)
                drawCell(gc, cell);
    }

    public boolean nextIteration() {
        for (int y = 0; y < height; y++)
            for (int x = 0; x < width; x++) {
                board[y][x].calculateOutput();
            }
        for (int y = 0; y < height; y++)
            for (int x = 0; x < width; x++) {
                board[y][x].collectInput();
            }
        currentMaxIteration++;
        return true;
    }

    public int getCurrentMaxIteration() {
        return currentMaxIteration;
    }

    public CellInterface getCell(int y, int x) {
        if (y < 0 || y >= height
                || x < 0 || x >= width)
            return null;
        return board[y][x];
    }
}
