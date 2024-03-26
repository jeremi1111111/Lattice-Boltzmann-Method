package com.lbm;

import javafx.scene.paint.Color;

import java.util.Collections;

public interface CellInterface {
    int DIMENSIONS = 2;
    int DIRECTIONS = 4;
    int[][] DIRECTION_VECTORS = new int[][] {
            {1, 0},
            {-1, 0},
            {0, 1},
            {0, -1}
    };
    double[] WEIGHTS = Collections.nCopies(DIRECTIONS, 1. / DIRECTIONS).stream().mapToDouble(Double::doubleValue).toArray();
    double RELAXATION_TIME = 1;
    double TIME_STEP = 1;

    double getCanvasX();
    double getCanvasY();
    int getBoardX();
    int getBoardY();
    Color getColor();
    void calculateOutput();
    void collectInput();
    double getOutput(int i);
    double getConcentration();
}
