package org.lbmbc.boundary;

import org.lbmbc.Board;

import java.util.Arrays;

public class Real extends BoundaryCondition{
    public Real(int boardX, int boardY, double canvasX, double canvasY, double density, double[] speed, Board owner) {
        super(boardX, boardY, canvasX, canvasY, density, speed, owner);

        directionVectors[nIndexes[2]] = directionVectors[nIndexes[6]];
        inputIndexes[nIndexes[2]] = inputIndexes[nIndexes[6]];
        directionVectors[nIndexes[1]] = directionVectors[nIndexes[7]];
        inputIndexes[nIndexes[1]] = inputIndexes[nIndexes[7]];
        directionVectors[nIndexes[0]] = directionVectors[nIndexes[8]];
        inputIndexes[nIndexes[0]] = inputIndexes[nIndexes[8]];
    }

    @Override
    public void collectInput() {
        super.collectInput();
        int i1 = nIndexes[8];
        int i2 = nIndexes[6];
        double in1 = input[i1];
        double in2 = input[i2];
        input[i1] = FRICTION * in1 + (1 - FRICTION) * in2;
        input[i2] = FRICTION * in2 + (1 - FRICTION) * in1;
        speed = Arrays.stream(speed).map(v -> v * FRICTION).toArray();
        denSpeed = Arrays.stream(speed).map(v -> v * density).toArray();
    }
}
