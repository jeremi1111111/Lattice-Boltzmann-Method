package org.lbmbc.boundary;

import org.lbmbc.Board;

public class BounceBack extends BoundaryCondition {
    public BounceBack(int boardX, int boardY, double canvasX, double canvasY, double density, double[] speed, Board owner) {
        super(boardX, boardY, canvasX, canvasY, density, speed, owner);

        if (boardX == BC_LEFT_X && boardY == BC_TOP_Y
                || boardX == BC_LEFT_X && boardY == BC_BOTTOM_Y
                || boardX == BC_RIGHT_X && boardY == BC_TOP_Y
                || boardX == BC_RIGHT_X && boardY == BC_BOTTOM_Y) {
            directionVectors[nIndexes[0]] = directionVectors[nIndexes[6]];
            inputIndexes[nIndexes[0]] = inputIndexes[nIndexes[6]];
            directionVectors[nIndexes[1]] = directionVectors[nIndexes[7]];
            inputIndexes[nIndexes[1]] = inputIndexes[nIndexes[7]];
            directionVectors[nIndexes[2]] = directionVectors[nIndexes[6]];
            inputIndexes[nIndexes[2]] = inputIndexes[nIndexes[6]];
            directionVectors[nIndexes[5]] = directionVectors[nIndexes[3]];
            inputIndexes[nIndexes[5]] = inputIndexes[nIndexes[3]];
            directionVectors[nIndexes[8]] = directionVectors[nIndexes[6]];
            inputIndexes[nIndexes[8]] = inputIndexes[nIndexes[6]];
        } else {
            directionVectors[nIndexes[0]] = directionVectors[nIndexes[8]];
            inputIndexes[nIndexes[0]] = inputIndexes[nIndexes[8]];
            directionVectors[nIndexes[1]] = directionVectors[nIndexes[7]];
            inputIndexes[nIndexes[1]] = inputIndexes[nIndexes[7]];
            directionVectors[nIndexes[2]] = directionVectors[nIndexes[6]];
            inputIndexes[nIndexes[2]] = inputIndexes[nIndexes[6]];
        }
    }

    @Override
    public void collectInput() {
        super.collectInput();
        this.denSpeed = new double[2];
        this.speed = new double[2];
    }
}
