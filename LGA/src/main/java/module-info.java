module com.lga {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.lga to javafx.fxml;
    exports com.lga;
}