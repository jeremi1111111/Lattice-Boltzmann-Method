package com.lbmflow;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

public class SpeedController {
    @FXML
    public Canvas c1;
    @FXML
    public Canvas c2;

    @FXML
    public void initialize() {
        double height = LBMController.HEIGHT;
        double width = LBMController.WIDTH;
        double cellSize = LBMController.CELL_SIZE;
        c1.setHeight(height * cellSize);
        c1.setWidth(width * cellSize);
        c2.setHeight(height * cellSize);
        c2.setWidth(width * cellSize);
    }

    public GraphicsContext getGC1() {
        return c1.getGraphicsContext2D();
    }

    public GraphicsContext getGC2() {
        return c2.getGraphicsContext2D();
    }
}
