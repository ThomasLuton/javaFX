package com.example.backlogtp;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Belle bite");
    }

    @FXML
    protected void onRemoveButtonClick(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Mais oui on t'aime quand même");
        alert.show();
        System.out.println();
    }
}