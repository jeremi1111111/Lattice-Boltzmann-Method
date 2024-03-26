package org.lbmbc.boundary;

import org.lbmbc.Board;

import java.util.Arrays;

public class Open extends BoundaryCondition {
    private final boolean isCorner;
    private final boolean isHSymmetric;
    private final int[] csIndexes;

    public Open(int boardX, int boardY, double canvasX, double canvasY, double density, double[] speed, Board owner) {
        super(boardX, boardY, canvasX, canvasY, density, speed, owner);

        if ((boardX == BC_LEFT_X && boardY == BC_TOP_Y)
                || (boardX == BC_LEFT_X && boardY == BC_BOTTOM_Y)
                || (boardX == BC_RIGHT_X && boardY == BC_TOP_Y)
                || (boardX == BC_RIGHT_X && boardY == BC_BOTTOM_Y)) {
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
            isCorner = true;
            int[] vecH = directionVectors[nIndexes[3]];
            isHSymmetric = board.getCell(boardY - vecH[0], boardX - vecH[1]) instanceof Symmetric;
            csIndexes = new int[]{
                    isHSymmetric ? 0 : 1,
                    isHSymmetric ? 1 : 0
            };
        } else {
            directionVectors[nIndexes[0]] = directionVectors[nIndexes[6]];
            inputIndexes[nIndexes[0]] = inputIndexes[nIndexes[6]];
            directionVectors[nIndexes[1]] = directionVectors[nIndexes[7]];
            inputIndexes[nIndexes[1]] = inputIndexes[nIndexes[7]];
            directionVectors[nIndexes[2]] = directionVectors[nIndexes[8]];
            inputIndexes[nIndexes[2]] = inputIndexes[nIndexes[8]];
            isCorner = false;
            isHSymmetric = false;
            csIndexes = null;
        }
    }

    @Override
    public void collectInput() {
        super.collectInput();

        speed = snType.normaliseSpeed(initSpeed);
        if (isCorner) {
            speed[csIndexes[0]] = 0.;
            if (initDensity == 0.)
                density = density / (1 - speed[csIndexes[1]]);
            else {
                speed[csIndexes[1]] = 1 - density / initDensity;
                density = initDensity;
            }
            double v = density * speed[csIndexes[1]] / 6.;
            input[nIndexes[0]] = input[nIndexes[6]];
            input[nIndexes[1]] = input[nIndexes[7]];
            input[nIndexes[2]] = input[nIndexes[6]] + v;
            input[nIndexes[5]] = input[nIndexes[3]];
            input[nIndexes[8]] = input[nIndexes[6]];
            if (isHSymmetric) {
                input[nIndexes[5]] += 4 * v;
                input[nIndexes[8]] += v;
            } else {
                input[nIndexes[1]] += 4 * v;
                input[nIndexes[0]] += v;
            }
        } else {
            if (initDensity == 0.)
                density = density / (1 - speed[0]);
            else {
                speed[0] = 1 - density / initDensity;
                // don't know if it's needed, but I assume yes
                density = initDensity;
            }
            input[nIndexes[0]] = input[nIndexes[6]];
            input[nIndexes[1]] = input[nIndexes[7]];
            input[nIndexes[2]] = input[nIndexes[8]];
            if (speed[1] != 0.) {
                speed[1] = 6 / density
                        * (input[nIndexes[5]] - input[nIndexes[3]]
                        + input[nIndexes[8]] - input[nIndexes[6]])
                        / (5 - 3 * speed[0]);
                speed[0] = 0.;
            } else {
                double v = density * speed[0] / 6.;
                input[nIndexes[0]] += v;
                input[nIndexes[1]] += 4 * v;
                input[nIndexes[2]] += v;
            }
        }
        speed = snType.normaliseSpeed(speed);
        denSpeed = Arrays.stream(speed).map(v -> v * density).toArray();
    }
}
