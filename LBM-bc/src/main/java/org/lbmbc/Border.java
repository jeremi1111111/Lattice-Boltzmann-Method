package org.lbmbc;

import javafx.scene.paint.Color;

public class Border implements CellInterface {
    private final int boardX;
    private final int boardY;
    private final double canvasX;
    private final double canvasY;

    public Border(int boardX, int boardY, double canvasX, double canvasY, Board owner) {
        this.boardX = boardX;
        this.boardY = boardY;
        this.canvasX = canvasX;
        this.canvasY = canvasY;
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
        return Color.GREY;
    }

    @Override
    public Color[] getSpeedColors() {
        return new Color[]{Color.GREY, Color.GREY};
    }

    @Override
    public void calculateOutput() {
        return;
    }

    @Override
    public void collectInput() {
        return;
    }

    @Override
    public double getOutput(int i) {
        return 0.;
    }
    @Override
    public double getDensity() {
        return 0;
    }

    @Override
    public double[] getSpeed() {
        return new double[2];
    }
}
