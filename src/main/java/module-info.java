module com.example.backlogtp {
    requires javafx.controls;
    requires javafx.fxml;
            
                            
    opens com.example.backlogtp to javafx.fxml;
    exports com.example.backlogtp;
}