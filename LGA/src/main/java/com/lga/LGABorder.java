package com.lga;

import javafx.scene.paint.Color;

public class LGABorder extends Cell{
    public LGABorder(int boardX, int boardY, double canvasX, double canvasY, Board owner) {
        super(boardX, boardY, canvasX, canvasY, owner);
        this.color = Color.GREY;
    }

    @Override
    public boolean[] getOutput(int pos) {
        boolean[] out = new boolean[4];
        switch (pos) {
            case 0:
                out[0] = board.getCell(boardY - 1, boardX).output[2];
                break;
            case 1:
                out[1] = board.getCell(boardY, boardX + 1).output[3];
                break;
            case 2:
                out[2] = board.getCell(boardY + 1, boardX).output[0];
                break;
            case 3:
                out[3] = board.getCell(boardY, boardX - 1).output[1];
                break;
        }
        return out;
    }

    @Override
    public Color getColor() {
        return Color.GREY;
    }
}
