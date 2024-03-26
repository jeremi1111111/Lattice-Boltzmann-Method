package org.lbmbc.boundary;

import org.lbmbc.Board;

import java.util.Arrays;

public class Symmetric extends BoundaryCondition{
    public Symmetric(int boardX, int boardY, double canvasX, double canvasY, double density, double[] speed, Board owner) {
        super(boardX, boardY, canvasX, canvasY, density, speed, owner);

        directionVectors[nIndexes[0]] = directionVectors[nIndexes[6]];
        inputIndexes[nIndexes[0]] = inputIndexes[nIndexes[6]];
        directionVectors[nIndexes[1]] = directionVectors[nIndexes[7]];
        inputIndexes[nIndexes[1]] = inputIndexes[nIndexes[7]];
        directionVectors[nIndexes[2]] = directionVectors[nIndexes[8]];
        inputIndexes[nIndexes[2]] = inputIndexes[nIndexes[8]];
    }

    @Override
    public void collectInput() {
        super.collectInput();
        double[] nSpeed = snType.normaliseSpeed(speed);
        nSpeed[0] = 0.;
        speed = snType.normaliseSpeed(nSpeed);
        denSpeed = Arrays.stream(speed).map(v -> v * density).toArray();
    }
}
