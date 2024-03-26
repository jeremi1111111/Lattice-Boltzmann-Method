package com.lga;

import javafx.scene.canvas.GraphicsContext;

import java.util.Arrays;

public class Board {
    private final double cellSize;
    private final int height;
    private final int width;
    private int currentMaxIteration = 0;
    private final Cell[][] board;

    public Board(boolean[][] stateMatrix, int cellSize) {
        this.cellSize = cellSize;
        this.height = stateMatrix.length;
        this.width = stateMatrix[0].length;

        this.board = new Cell[height][width];
        for (int y = 0; y < height; y++)
            for (int x = 0; x < width; x++)
                board[y][x] = (stateMatrix[y][x] ?
                        new LGABorder(x, y, cellSize * x, cellSize * y, this) :
                        new LGACell(x, y, cellSize * x, cellSize * y, this));
    }

    public void addInput(int y, int x, int pos) {
        board[y][x].setInput(pos);
    }

    private void drawCell(GraphicsContext gc, Cell cell) {
        gc.setFill(cell.getColor());
        gc.fillRect(cell.getCanvasX(), cell.getCanvasY(), cellSize, cellSize);
    }

    public void drawCanvas(GraphicsContext gc) {
        for (Cell[] row : board)
            for (Cell cell : row)
                drawCell(gc, cell);
    }

    public boolean nextIteration() {
        boolean flag = false;
        for (int y = 0; y < height; y++)
            for (int x = 0; x < width; x++) {
                if (board[y][x] instanceof LGABorder)
                    continue;
                board[y][x].calculateOutput();
            }
        for (int y = 0; y < height; y++)
            for (int x = 0; x < width; x++) {
                if (board[y][x] instanceof LGABorder)
                    continue;
                board[y][x].setInput(board[y - 1][x].getOutput(2));
                board[y][x].setInput(board[y][x + 1].getOutput(3));
                board[y][x].setInput(board[y + 1][x].getOutput(0));
                board[y][x].setInput(board[y][x - 1].getOutput(1));
            }
        currentMaxIteration++;
        return true;
    }

    public int getCurrentMaxIteration() {
        return currentMaxIteration;
    }

    public Cell getCell(int y, int x) {
        if (y < 0 || y >= height
                || x < 0 || x >= width)
            return null;
        return board[y][x];
    }

    @Override
    public String toString() {
        return "Board{" +
                "board=" + Arrays.toString(board) +
                '}';
    }
}
