module com.example.backlogtp {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.sql;
    requires jdk.xml.dom;

    opens com.example.backlogtp to javafx.fxml;
    opens com.example.backlogtp.ui to javafx.fxml;
    exports com.example.backlogtp;
    exports com.example.backlogtp.ui;
}