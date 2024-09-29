module com.example.dsiigui {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;

    opens com.example.dsiigui to javafx.fxml;
    exports com.example.dsiigui;
}