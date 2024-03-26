package com.lbm;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.util.Duration;

import java.util.Random;

public class LBMController {
    private static final int HEIGHT = 50;
    private static final int WIDTH = 50;
    private static final int CELL_SIZE = 16;
    private static final double BOX_OPENING = 0.4;
    private static final double OPENING_SIZE = 0.4;

    @FXML
    public Canvas canvas;
    @FXML
    public HBox controlPanel;
    @FXML
    public TextField iteration;
    @FXML
    Animation animation;

    private Board board;

    @FXML
    public void initialize() {
        canvas.setWidth(WIDTH * CELL_SIZE);
        canvas.setHeight(HEIGHT * CELL_SIZE);
        iteration.setText("0");
        controlPanel.setDisable(true);

        double[][] stateMatrix = new double[HEIGHT][WIDTH];
        final int holeX = (int) (WIDTH * BOX_OPENING);
        final int holeTopY = (int) (HEIGHT * (1. - OPENING_SIZE) * 0.5);
        final int holeBottomY = HEIGHT - holeTopY;
        for (int x = 0; x < WIDTH; x++) {
            stateMatrix[0][x] = -1.;
            stateMatrix[HEIGHT - 1][x] = -1.;
        }
        for (int y = 1; y < HEIGHT - 1; y++) {
            stateMatrix[y][0] = -1.;
            for (int x = 1; x < holeX; x++)
                stateMatrix[y][x] = 1.;
            stateMatrix[y][holeX] = y > holeTopY && y < holeBottomY ? 0. : -1.;
            for (int x = holeX + 1; x < WIDTH - 1; x++)
                stateMatrix[y][x] = 0.;
            stateMatrix[y][WIDTH - 1] = -1.;
        }

        board = new Board(stateMatrix, CELL_SIZE);
        board.drawCanvas(canvas.getGraphicsContext2D());
    }

    @FXML
    public void onSimulateButtonClick(MouseEvent mouseEvent) {
        if (animation != null) {
            if (animation.getStatus() == Animation.Status.RUNNING)
                animation.pause();
            else if (animation.getStatus() == Animation.Status.PAUSED ||
                    animation.getStatus() == Animation.Status.STOPPED)
                animation.play();
            return;
        }

        GraphicsContext gc = canvas.getGraphicsContext2D();
        controlPanel.setDisable(false);

        animation = new Timeline(new KeyFrame(Duration.millis(20), (event) -> {
            if (board.nextIteration()) {
                board.drawCanvas(gc);
            }
            else {
                controlPanel.setDisable(true);
                animation.stop();
            }
            iteration.setText(String.valueOf(board.getCurrentMaxIteration()));
        }));
        animation.setCycleCount(Animation.INDEFINITE);
        animation.play();
    }
}