module org.lbmbc {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.lbmbc to javafx.fxml;
    exports org.lbmbc;
    exports org.lbmbc.boundary;
    opens org.lbmbc.boundary to javafx.fxml;
}