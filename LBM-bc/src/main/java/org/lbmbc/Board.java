package org.lbmbc;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.lbmbc.boundary.*;

import static org.lbmbc.CellInterface.DIMENSIONS;

public class Board {
    private final double cellSize;
    private final int height;
    private final int width;
    private int currentMaxIteration = 0;
    private final CellInterface[][] board;

    public Board(CellType[][] cellTypeMatrix, double[][] densityMatrix, double[][] speedMatrixY, double[][] speedMatrixX, int cellSize) {
        this.cellSize = cellSize;
        this.height = cellTypeMatrix.length;
        this.width = cellTypeMatrix[0].length;

        this.board = new CellInterface[height][width];
        for (int y = 0; y < height; y++)
            for (int x = 0; x < width; x++)
                board[y][x] = switch (cellTypeMatrix[y][x]) {
                    case CELL -> new Cell(x, y, cellSize * x, cellSize * y,
                                densityMatrix[y][x], new double[]{speedMatrixY[y][x], speedMatrixX[y][x]}, this);
                    case BORDER -> new Border(x, y, cellSize * x, cellSize * y, this);
                    case CONSTANT -> new Constant(x, y, cellSize * x, cellSize * y,
                            densityMatrix[y][x], new double[]{speedMatrixY[y][x], speedMatrixX[y][x]}, this);
                    case OPEN -> new Open(x, y, cellSize * x, cellSize * y,
                            densityMatrix[y][x], new double[]{speedMatrixY[y][x], speedMatrixX[y][x]}, this);
                    case BOUNCE_BACK -> new BounceBack(x, y, cellSize * x, cellSize * y,
                            densityMatrix[y][x], new double[]{speedMatrixY[y][x], speedMatrixX[y][x]}, this);
                    case SYMMETRIC -> new Symmetric(x, y, cellSize * x, cellSize * y,
                            densityMatrix[y][x], new double[]{speedMatrixY[y][x], speedMatrixX[y][x]}, this);
                    case REAL -> new Real(x, y, cellSize * x, cellSize * y,
                            densityMatrix[y][x], new double[]{speedMatrixY[y][x], speedMatrixX[y][x]}, this);
                    default -> null;
                };
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
        System.out.println(totalDensity);
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
