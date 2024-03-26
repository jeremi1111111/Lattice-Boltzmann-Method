package org.lbmbc;

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
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.Random;

public class LBMController {
    public static final int HEIGHT = 100;
    public static final int WIDTH = 100;
    public static final int CELL_SIZE = 4;

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

        CellType[][] cellTypeMatrix = new CellType[HEIGHT][WIDTH];
        double[][] densityMatrix = new double[HEIGHT][WIDTH];
        double[][] speedMatrixY = new double[HEIGHT][WIDTH];
        double[][] speedMatrixX = new double[HEIGHT][WIDTH];
        for (int y = 2; y < HEIGHT - 2; y++)
            for (int x = 2; x < WIDTH - 2; x++) {
                cellTypeMatrix[y][x] = CellType.CELL;
                densityMatrix[y][x] = 1.;
                speedMatrixY[y][x] = 0.;
                speedMatrixX[y][x] = 0.;
            }
        for (int x = 0; x < WIDTH; x++) {
            cellTypeMatrix[0][x] = CellType.BORDER;
            cellTypeMatrix[HEIGHT - 1][x] = CellType.BORDER;
        }
        for (int y = 1; y < HEIGHT - 1; y++) {
            cellTypeMatrix[y][0] = CellType.BORDER;
            cellTypeMatrix[y][WIDTH - 1] = CellType.BORDER;
        }

        // top left
        cellTypeMatrix[1][1] = CellType.BOUNCE_BACK;
        densityMatrix[1][1] = 1.;
        speedMatrixY[1][1] = 0.;
        speedMatrixX[1][1] = 0.;
        // bottom left
        cellTypeMatrix[HEIGHT - 2][1] = CellType.BOUNCE_BACK;
        densityMatrix[HEIGHT - 2][1] = 1.;
        speedMatrixY[HEIGHT - 2][1] = 0.;
        speedMatrixX[HEIGHT - 2][1] = 0.;
        // top right
        cellTypeMatrix[1][WIDTH - 2] = CellType.BOUNCE_BACK;
        densityMatrix[1][WIDTH - 2] = 1.;
        speedMatrixY[1][WIDTH - 2] = 0.;
        speedMatrixX[1][WIDTH - 2] = 0.;
        // bottom right
        cellTypeMatrix[HEIGHT - 2][WIDTH - 2] = CellType.BOUNCE_BACK;
        densityMatrix[HEIGHT - 2][WIDTH - 2] = 1.;
        speedMatrixY[HEIGHT - 2][WIDTH - 2] = 0.;
        speedMatrixX[HEIGHT - 2][WIDTH - 2] = 0.;

        // variant 1
        /**/
        for (int x = 2; x < WIDTH-2; x++) {
            // top border
            cellTypeMatrix[1][x] = CellType.SYMMETRIC;
            densityMatrix[1][x] = 1.;
            speedMatrixY[1][x] = 0.;
            speedMatrixX[1][x] = 0.;

            // bottom border
            cellTypeMatrix[HEIGHT - 2][x] = CellType.BOUNCE_BACK;
            densityMatrix[HEIGHT - 2][x] = 1.;
            speedMatrixY[HEIGHT - 2][x] = 0.;
            speedMatrixX[HEIGHT - 2][x] = 0.;
        }
        for (int y = 2; y < HEIGHT - 2; y++) {
            // left border
            cellTypeMatrix[y][1] = CellType.OPEN;
            densityMatrix[y][1] = 0.;
            speedMatrixY[y][1] = 0.;
            speedMatrixX[y][1] = 0.25 * (HEIGHT - y - 2) / (HEIGHT - 3);

            // right border
            cellTypeMatrix[y][WIDTH - 2] = CellType.OPEN;
            densityMatrix[y][WIDTH - 2] = 1.;
            speedMatrixY[y][WIDTH - 2] = 0.;
            speedMatrixX[y][WIDTH - 2] = 0.;
        }
         /**/

        // variant 2
        /*
        for (int x = 1; x < WIDTH-1; x++) {
            // top border
            cellTypeMatrix[1][x] = CellType.CONSTANT;
            densityMatrix[1][x] = 1.;
            speedMatrixY[1][x] = 0.;
            speedMatrixX[1][x] = 0.25;

            // bottom border
            cellTypeMatrix[HEIGHT - 2][x] = CellType.CONSTANT;
            densityMatrix[HEIGHT - 2][x] = 1.;
            speedMatrixY[HEIGHT - 2][x] = 0.;
            speedMatrixX[HEIGHT - 2][x] = 0.;
        }
        for (int y = 2; y < HEIGHT - 2; y++) {
            // left border
            cellTypeMatrix[y][1] = CellType.CONSTANT;
            densityMatrix[y][1] = 1.;
            speedMatrixY[y][1] = 0.;
            speedMatrixX[y][1] = 0.25 * (HEIGHT - y - 2) / (HEIGHT - 3);

            // right border
            cellTypeMatrix[y][WIDTH - 2] = CellType.CONSTANT;
            densityMatrix[y][WIDTH - 2] = 1.;
            speedMatrixY[y][WIDTH - 2] = 0.;
            speedMatrixX[y][WIDTH - 2] = 0.25 * (HEIGHT - y - 2) / (HEIGHT - 3);
        }
         */

        board = new Board(cellTypeMatrix, densityMatrix, speedMatrixY, speedMatrixX, CELL_SIZE);
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

        GraphicsContext gc = canvas.getGraphicsContext2D();
        controlPanel.setDisable(false);
        iteration.setText("0");

        Stage speedStage = new Stage();
        FXMLLoader loader = new FXMLLoader(LBMApplication.class.getResource("speed-view.fxml"));
        Scene scene = new Scene(loader.load());
        speedStage.setScene(scene);
        SpeedController speedController = loader.getController();
        GraphicsContext gc1 = speedController.getGC1();
        GraphicsContext gc2 = speedController.getGC2();
        board.drawSpeed(gc1, gc2);
        speedStage.setTitle("Flow speeds");
        speedStage.show();

        animation = new Timeline(new KeyFrame(Duration.millis(50), (event) -> {
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