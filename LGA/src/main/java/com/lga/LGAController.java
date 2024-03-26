package com.lga;

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

public class LGAController {
    private static final int HEIGHT = 200;
    private static final int WIDTH = 200;
    private static final int BORDER_SIZE = 1;
    private static final int CELL_SIZE = 4;

    @FXML
    public Canvas canvas;
    @FXML
    public HBox controlPanel;
    @FXML
    public TextField iteration;
    @FXML
    Animation animation;

    private Board board;
    Random rand = new Random();

    @FXML
    public void initialize() {
        canvas.setWidth(WIDTH * CELL_SIZE);
        canvas.setHeight(HEIGHT * CELL_SIZE);
        controlPanel.setDisable(true);

        boolean[][] stateMatrix = new boolean[HEIGHT][WIDTH];
        for (int i = 0; i < HEIGHT; i++)
            for (int j = 0; j < WIDTH; j++) {
                if (j > Math.floor(0.25 * WIDTH - 0.5 * BORDER_SIZE)
                        && j < Math.ceil(0.25 * WIDTH + 0.5 * BORDER_SIZE)
                        && (i < 0.4 * HEIGHT || i > 0.6 * HEIGHT))
                    stateMatrix[i][j] = true;
                else stateMatrix[i][j] = i < BORDER_SIZE || i >= HEIGHT - BORDER_SIZE
                        || j < BORDER_SIZE || j >= WIDTH - BORDER_SIZE;
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
        for (int i = BORDER_SIZE; i < HEIGHT - BORDER_SIZE; i++)
            for (int j = BORDER_SIZE; j <= Math.floor(0.25 * WIDTH - 0.5 * BORDER_SIZE); j++) {
                int res = rand.nextInt(8);
                if (res < 4)
                    board.addInput(i, j, res);
            }

        GraphicsContext gc = canvas.getGraphicsContext2D();
        board.drawCanvas(gc);
        controlPanel.setDisable(false);
        iteration.setText("0");

        animation = new Timeline(new KeyFrame(Duration.millis(25), (event) -> {
            if (board.nextIteration())
                board.drawCanvas(gc);
            else {
                controlPanel.setDisable(true);
                animation.stop();
            }
            iteration.setText(String.valueOf(board.getCurrentMaxIteration()));
        }));
        animation.setCycleCount(Animation.INDEFINITE);
    }
}