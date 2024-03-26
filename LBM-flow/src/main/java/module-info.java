module com.lbmflow {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.lbmflow to javafx.fxml;
    exports com.lbmflow;
}