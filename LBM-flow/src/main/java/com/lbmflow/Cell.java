package com.lbmflow;

import javafx.scene.paint.Color;

import java.util.Arrays;

public class Cell implements CellInterface {
    private final int boardX;
    private final int boardY;
    private final double canvasX;
    private final double canvasY;
    private final Board board;
    private final double[] input;
    private final double[] output;
    private double density;
    private double[] den_speed;
    private double[] speed;

    public Cell(int boardX, int boardY, double canvasX, double canvasY, double density, double[] speed, Board owner) {
        this.boardX = boardX;
        this.boardY = boardY;
        this.canvasX = canvasX;
        this.canvasY = canvasY;
        this.board = owner;
        input = new double[DIRECTIONS];
        output = new double[DIRECTIONS];
        this.density = density;
        this.den_speed = Arrays.stream(speed).map(v -> v * density).toArray();
        this.speed = speed;
    }

    protected double equalState(int i) {
        if (density == 0.)
            return 0.;
        double[] vec = {
                DIRECTION_VECTORS[i][0] * den_speed[0],
                DIRECTION_VECTORS[i][1] * den_speed[1]
        };
        return WEIGHTS[i] *
                (density + 3 * (vec[0] + vec[1])
                        + 4.5 * Math.pow(vec[0] + vec[1], 2)
                        - 1.5 * (Math.pow(den_speed[0], 2) + Math.pow(den_speed[1], 2)));
    }

    @Override
    public void calculateOutput() {
        for (int i = 0; i < DIRECTIONS; i++) {
            output[i] = input[i] + TIME_STEP / RELAXATION_TIME * (equalState(i) - input[i]);
        }
    }

    @Override
    public void collectInput() {
        den_speed = new double[DIMENSIONS];
        for (int i = 0; i < DIRECTIONS; i++) {
            int[] vec = DIRECTION_VECTORS[i];
            input[i] = board.getCell(boardY - vec[0], boardX - vec[1]).getOutput(i);
            den_speed[0] += vec[0] * input[i];
            den_speed[1] += vec[1] * input[i];
        }
        density = Arrays.stream(input).sum();
        speed = Arrays.stream(den_speed).map(v -> density == 0. ? 0. : v / density).toArray();
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
        return Color.WHITE.interpolate(Color.RED, density);
    }

    @Override
    public Color[] getSpeedColors() {
        return new Color[]{
                Color.WHITE.interpolate(speed[0] > 0. ? Color.BLUE : Color.RED, Math.abs(speed[0])),
                Color.WHITE.interpolate(speed[1] > 0. ? Color.RED : Color.BLUE, Math.abs(speed[1]))
        };
    }

    @Override
    public double getDensity() {
        return density;
    }

    @Override
    public double[] getSpeed() {
        return speed;
    }

    @Override
    public String toString() {
        return density + "\n" + Arrays.toString(speed) + "\n" + Arrays.toString(input) + "\n" + Arrays.toString(output);
    }
}
