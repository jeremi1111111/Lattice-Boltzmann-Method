package com.lga;

import javafx.scene.paint.Color;

import java.util.Arrays;

public class LGACell extends Cell {
    private static final boolean[] REFLECT_1 = new boolean[]{false, true, false, true};
    private static final boolean[] REFLECT_2 = new boolean[]{true, false, true, false};
    public LGACell(int boardX, int boardY, double canvasX, double canvasY, Board owner) {
        super(boardX, boardY, canvasX, canvasY, owner);
        this.color = Color.WHITE;
    }

    @Override
    public void calculateOutput() {
        if (Arrays.equals(input, REFLECT_1))
            output = REFLECT_2;
        else if (Arrays.equals(input, REFLECT_2))
            output = REFLECT_1;
        else
            output = input;
        input = new boolean[]{false, false, false, false};
    }

    @Override
    public Color getColor() {
        if (input[0] || input[1] || input[2] || input[3])
            return Color.BLACK;
        else
            return Color.WHITE;
    }
}
