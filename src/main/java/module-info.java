module com.example.backlogtp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires javafx.graphics;
    requires jdk.xml.dom;


    opens com.example.backlogtp to javafx.fxml;
    exports com.example.backlogtp;
    exports com.example.backlogtp.ui;
}