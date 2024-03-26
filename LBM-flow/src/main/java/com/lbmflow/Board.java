package com.lbmflow;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import static com.lbmflow.CellInterface.DIMENSIONS;

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
                        new Cell(x, y, cellSize * x, cellSize * y, stateMatrix[y][x], new double[DIMENSIONS], this) :
                        new Border(x, y, cellSize * x, cellSize * y, this));
    }

    public Board(Board board) {
        this.cellSize = board.cellSize;
        this.height = board.height;
        this.width = board.width;
        this.currentMaxIteration = board.getCurrentMaxIteration();
        this.board = board.board.clone();
    }

    public void drawDensity(GraphicsContext gc) {
        for (CellInterface[] row : board)
            for (CellInterface cell : row) {
                gc.setFill(cell.getColor());
                gc.fillRect(cell.getCanvasX(), cell.getCanvasY(), cellSize, cellSize);
            }
    }

    public void drawSpeed(GraphicsContext gc1, GraphicsContext gc2) {
        for (CellInterface[] row : board)
            for (CellInterface cell : row) {
                Color[] speedColors = cell.getSpeedColors();
                gc1.setFill(speedColors[0]);
                gc1.fillRect(cell.getCanvasX(), cell.getCanvasY(), cellSize, cellSize);
                gc2.setFill(speedColors[1]);
                gc2.fillRect(cell.getCanvasX(), cell.getCanvasY(), cellSize, cellSize);
            }
    }

    public boolean nextIteration() {
        double totalDensity = 0.;
        for (int y = 0; y < height; y++)
            for (int x = 0; x < width; x++) {
                board[y][x].calculateOutput();
                totalDensity += board[y][x].getDensity();
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
