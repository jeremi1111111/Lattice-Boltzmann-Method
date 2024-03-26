package com.lbm;

import javafx.scene.paint.Color;

public class Border implements CellInterface {
    private final int boardX;
    private final int boardY;
    private final double canvasX;
    private final double canvasY;
    private final Board board;

    public Border(int boardX, int boardY, double canvasX, double canvasY, Board owner) {
        this.boardX = boardX;
        this.boardY = boardY;
        this.canvasX = canvasX;
        this.canvasY = canvasY;
        this.board = owner;
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
    public void calculateOutput() {
        return;
    }

    @Override
    public void collectInput() {
        return;
    }

    @Override
    public double getOutput(int i) {
        int[] vec = DIRECTION_VECTORS[i];
        return board.getCell(boardY + vec[0], boardX + vec[1]).getOutput(i + vec[0] + vec[1]);
    }

    @Override
    public double getConcentration() {
        return 0;
    }
}
