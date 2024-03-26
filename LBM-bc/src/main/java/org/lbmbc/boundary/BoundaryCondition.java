package org.lbmbc.boundary;

import org.lbmbc.Board;
import org.lbmbc.Cell;

import java.util.Arrays;

import static org.lbmbc.LBMController.HEIGHT;
import static org.lbmbc.LBMController.WIDTH;

public abstract class BoundaryCondition extends Cell {
    protected final static double FRICTION = 0.5;
    protected final static int BC_TOP_Y = 1;
    protected final static int BC_BOTTOM_Y = HEIGHT - 2;
    protected final static int BC_LEFT_X = 1;
    protected final static int BC_RIGHT_X = WIDTH - 2;
    private final static int[] INDEXES_R = new int[]{
            6, 3, 0,
            7, 4, 1,
            8, 5, 2
    };
    private final static int[] INDEXES_L = new int[]{
            2, 5, 8,
            1, 4, 7,
            0, 3, 6
    };
    private final static int[] INDEXES_2R = new int[]{
            8, 7, 6,
            5, 4, 3,
            2, 1, 0
    };
    protected int[][] directionVectors;
    protected int[] inputIndexes;
    protected final int[] nIndexes;
    protected final double initDensity;
    protected final double[] initSpeed;

    protected enum SpeedNormalisationType {
        DEFAULT(new int[]{0, 1}, new double[]{1, 1}),
        NEG_Y(new int[]{0, 1}, new double[]{-1, 1}),
        SWAP(new int[]{1, 0}, new double[]{1, 1}),
        NEG_X(new int[]{0, 1}, new double[]{1, -1}),
        SWAP_NEG(new int[]{1, 0}, new double[]{-1, -1});
        public final int[] indexes;
        public final double[] signs;

        SpeedNormalisationType(int[] indexes, double[] signs) {
            this.indexes = indexes;
            this.signs = signs;
        }

        public double[] normaliseSpeed(double[] speed) {
            return new double[]{
                    speed[indexes[0]] * signs[0],
                    speed[indexes[1]] * signs[1]
            };
        }
    }

    protected final SpeedNormalisationType snType;

    public BoundaryCondition(int boardX, int boardY, double canvasX, double canvasY, double density, double[] speed, Board owner) {
        super(boardX, boardY, canvasX, canvasY, density, speed, owner);
        directionVectors = new int[DIRECTIONS][];
        for (int i = 0; i < DIRECTIONS; i++)
            directionVectors[i] = Arrays.stream(DIRECTION_VECTORS[i]).toArray();
        inputIndexes = Arrays.stream(INDEXES).toArray();
        initDensity = density;
        initSpeed = speed;

        if (boardY == BC_TOP_Y && boardX != BC_LEFT_X) {
            nIndexes = INDEXES_2R;
            snType = SpeedNormalisationType.NEG_X;
        } else if (boardX == BC_RIGHT_X) {
            nIndexes = INDEXES_R;
            snType = SpeedNormalisationType.SWAP_NEG;
        } else if (boardY == BC_BOTTOM_Y) {
            nIndexes = INDEXES;
            snType = SpeedNormalisationType.NEG_Y;
        } else if (boardX == BC_LEFT_X) {
            nIndexes = INDEXES_L;
            snType = SpeedNormalisationType.SWAP;
        } else {
            nIndexes = INDEXES;
            snType = SpeedNormalisationType.DEFAULT;
        }
    }

    @Override
    public void collectInput() {
        denSpeed = new double[DIMENSIONS];
        for (int i = 0; i < DIRECTIONS; i++) {
            int[] vec = directionVectors[i];
            input[i] = board.getCell(boardY - vec[0], boardX - vec[1]).getOutput(inputIndexes[i]);
            denSpeed[0] += vec[0] * input[i];
            denSpeed[1] += vec[1] * input[i];
        }
        density = Arrays.stream(input).sum();
        speed = Arrays.stream(denSpeed).map((v) -> density == 0. ? 0. : v / density).toArray();
    }
}
