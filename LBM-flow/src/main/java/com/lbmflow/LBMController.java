package com.lbmflow;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.Random;

public class LBMController {
    public static final int HEIGHT = 100;
    public static final int WIDTH = 100;
    public static final int CELL_SIZE = 8;
    public static final double BOX_OPENING = 0.4;
    public static final double OPENING_SIZE = 0.3;
    public static GraphicsContext gc1;
    public static GraphicsContext gc2;

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
        controlPanel.setDisable(true);

        double[][] stateMatrix = new double[HEIGHT][WIDTH];
        final int holeX = (int) (BOX_OPENING == 1. ? WIDTH - 1 : WIDTH * BOX_OPENING);
        final int holeTopY = (int) (OPENING_SIZE == 1. ? 0. : HEIGHT * (1. - OPENING_SIZE) * 0.5);
        final int holeBottomY = HEIGHT - holeTopY;
        for (int x = 0; x < WIDTH; x++) {
            stateMatrix[0][x] = -1.;
            stateMatrix[HEIGHT - 1][x] = -1.;
        }
        for (int y = 1; y < HEIGHT - 1; y++) {
            stateMatrix[y][0] = -1.;
            for (int x = 1; x < holeX; x++)
                stateMatrix[y][x] = 1;
            stateMatrix[y][holeX] = y > holeTopY && y < holeBottomY ? 0. : -1.;
            for (int x = holeX + 1; x < WIDTH - 1; x++)
                stateMatrix[y][x] = 0.;
            stateMatrix[y][WIDTH - 1] = -1.;
        }

        board = new Board(stateMatrix, CELL_SIZE);
        board.drawDensity(canvas.getGraphicsContext2D());
    }

    @FXML
    public void onSimulateButtonClick(MouseEvent mouseEvent) throws IOException {
        if (animation != null) {
            if (animation.getStatus() == Animation.Status.RUNNING)
                animation.pause();
            else if (animation.getStatus() == Animation.Status.PAUSED ||
                    animation.getStatus() == Animation.Status.STOPPED)
                animation.play();
            return;
        }

        if (board.getCurrentMaxIteration() == 0) {
            Stage speedStage = new Stage();
            FXMLLoader loader = new FXMLLoader(LBMApplication.class.getResource("speed-view.fxml"));
            Scene scene = new Scene(loader.load());
            speedStage.setScene(scene);
            SpeedController speedController = loader.getController();
            gc1 = speedController.getGC1();
            gc2 = speedController.getGC2();
            board.drawSpeed(gc1, gc2);
            speedStage.setTitle("Flow speeds");
            speedStage.show();

            controlPanel.setDisable(false);
            iteration.setText("0");
        }

        GraphicsContext gc = canvas.getGraphicsContext2D();

        animation = new Timeline(new KeyFrame(Duration.millis(100), (event) -> {
            if (board.nextIteration()) {
                board.drawDensity(gc);
                board.drawSpeed(gc1, gc2);
            } else {
                controlPanel.setDisable(true);
                animation.stop();
            }
            iteration.setText(String.valueOf(board.getCurrentMaxIteration()));
        }));
        animation.setCycleCount(Animation.INDEFINITE);
    }
}