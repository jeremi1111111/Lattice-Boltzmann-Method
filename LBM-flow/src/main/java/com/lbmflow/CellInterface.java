package com.lbmflow;

import javafx.scene.paint.Color;

import java.util.Collections;

public interface CellInterface {
    int DIMENSIONS = 2;
    int DIRECTIONS = 9;
    int[][] DIRECTION_VECTORS = new int[][]{
            {-1, -1},   {-1, 0},    {-1, 1},
            {0, -1},    {0, 0},     {0, 1},
            {1, -1},    {1, 0},     {1, 1}
    };
    int[] REV_INDEX = new int[]{
            8, 7, 6,
            5, 4, 3,
            2, 1, 0
    };
    double[] WEIGHTS = new double[]{
            1. / 36., 1. / 9., 1. / 36.,
            1. / 9., 4. / 9., 1. / 9.,
            1. / 36., 1. / 9., 1. / 36.
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
