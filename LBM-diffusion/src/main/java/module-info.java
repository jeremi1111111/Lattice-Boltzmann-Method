module com.lbm {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.lbm to javafx.fxml;
    exports com.lbm;
}