package org.lbmbc;

import javafx.scene.paint.Color;

public interface CellInterface {
    int DIMENSIONS = 2;
    int DIRECTIONS = 9;
    int[][] DIRECTION_VECTORS = new int[][]{
            {-1, -1},   {-1, 0},    {-1, 1},
            {0, -1},    {0, 0},     {0, 1},
            {1, -1},    {1, 0},     {1, 1}
    };
    int[] INDEXES = new int[]{
            0, 1, 2,
            3, 4, 5,
            6, 7, 8
    };
    double[] WEIGHTS = new double[]{
            1. / 36.,   1. / 9.,    1. / 36.,
            1. / 9.,    4. / 9.,    1. / 9.,
            1. / 36.,   1. / 9.,    1. / 36.
    };
    double RELAXATION_TIME = 1;
    double TIME_STEP = 1;

    double getCanvasX();

    double getCanvasY();

    int getBoardX();

    int getBoardY();

    Color getColor();

    Color[] getSpeedColors();

    void calculateOutput();

    void collectInput();

    double getOutput(int i);
    double getDensity();
    double[] getSpeed();
}
