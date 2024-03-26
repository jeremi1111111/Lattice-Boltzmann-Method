package org.lbmbc.boundary;

import javafx.scene.paint.Color;
import org.lbmbc.Board;

public class Constant extends BoundaryCondition{
    private final Color densityColor;
    private final Color[] speedColors;
    public Constant(int boardX, int boardY, double canvasX, double canvasY, double density, double[] speed, Board owner) {
        super(boardX, boardY, canvasX, canvasY, density, speed, owner);

        for (int i = 0; i < DIRECTIONS; i++) {
            input[i] = equalState(i);
            output[i] = input[i];
        }
        densityColor = super.getColor();
        speedColors = super.getSpeedColors();
    }

    @Override
    public void collectInput() {
        return;
    }

    @Override
    public void calculateOutput() {
        return;
    }

    @Override
    public Color getColor() {
        return densityColor;
    }

    @Override
    public Color[] getSpeedColors() {
        return speedColors;
    }
}
