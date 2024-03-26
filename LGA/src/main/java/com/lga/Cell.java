package com.lga;

import javafx.scene.paint.Color;

import java.util.Arrays;

public class Cell {
    protected final int boardX;
    protected final int boardY;
    protected final double canvasX;
    protected final double canvasY;
    protected final Board board;
    protected Color color;
    protected boolean[] input;
    protected boolean[] output;

    public Cell(int boardX, int boardY, double canvasX, double canvasY, Board owner) {
        this.boardX = boardX;
        this.boardY = boardY;
        this.canvasX = canvasX;
        this.canvasY = canvasY;
        this.board = owner;
        input = new boolean[]{false, false, false, false};
        output = new boolean[]{false, false, false, false};
    }

    public double getCanvasX() {
        return canvasX;
    }

    public double getCanvasY() {
        return canvasY;
    }

    public int getBoardX() {
        return boardX;
    }

    public int getBoardY() {
        return boardY;
    }

    public int getCellCount() {
        int c = 0;
        for (boolean v : input)
            if (v) c++;
        return c;
    }

    public void setInput(int pos) {
        input[pos] = true;
        color = Color.BLACK;
    }

    public void setInput(boolean[] inp) {
        for (int i = 0; i < 4; i++)
            input[i] = input[i] || inp[i];
    }

    public void calculateOutput() {}

    public boolean[] getOutput() {
        return output;
    }

    public boolean[] getOutput(int pos) {
        boolean[] out = new boolean[4];
        out[pos] = output[pos];
        return out;
    }

    public Color getColor() {
        return Color.WHITE;
    }

    @Override
    public String toString() {
        return Arrays.toString(input);
    }
}
