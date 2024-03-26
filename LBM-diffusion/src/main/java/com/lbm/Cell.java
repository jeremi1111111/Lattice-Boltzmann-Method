package com.lbm;

import javafx.scene.paint.Color;

import java.util.Arrays;
import java.util.Collections;

public class Cell implements CellInterface {
    private final int boardX;
    private final int boardY;
    private final double canvasX;
    private final double canvasY;
    private final Board board;
    private final double[] input;
    private final double[] output;
    private double concentration;

    public Cell(int boardX, int boardY, double canvasX, double canvasY, double concentration, Board owner) {
        this.boardX = boardX;
        this.boardY = boardY;
        this.canvasX = canvasX;
        this.canvasY = canvasY;
        this.concentration = concentration;
        this.board = owner;
        input = new double[DIRECTIONS];
        output = new double[DIRECTIONS];
    }

    private double equalState(int i) {
        return WEIGHTS[i] * concentration;
    }

    @Override
    public void calculateOutput() {
        for (int i = 0; i < DIRECTIONS; i++) {
            output[i] = input[i] + TIME_STEP / RELAXATION_TIME * (equalState(i) - input[i]);
            input[i] = 0.;
        }
    }

    @Override
    public void collectInput() {
        concentration = 0.;
        for (int i = 0; i < DIRECTIONS; i++) {
            int[] vec = DIRECTION_VECTORS[i];
            input[i] = board.getCell(boardY - vec[0], boardX - vec[1]).getOutput(i);
        }
        concentration = Arrays.stream(input).sum();
    }

    @Override
    public double getOutput(int i) {
        return output[i];
    }

    @Override
    public double getCanvasX() {
        return canvasX;
    }

    @Override
    public double getCanvasY() {
        return canvasY;
    }

    @Override
    public int getBoardX() {
        return boardX;
    }

    @Override
    public int getBoardY() {
        return boardY;
    }

    @Override
    public Color getColor() {
        return Color.WHITE.interpolate(Color.RED, concentration);
    }

    @Override
    public double getConcentration() {
        return concentration;
    }
}
