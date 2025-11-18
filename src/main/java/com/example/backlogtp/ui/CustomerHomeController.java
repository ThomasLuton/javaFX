package com.example.backlogtp.ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class CustomerHomeController {

    @FXML
    private VBox eventContainer;

    @FXML
    private void initialize() {
        eventContainer.getStylesheets().add("components.css");

        HBox newEvent =  new HBox(5);
        newEvent.setMaxWidth(500);
        newEvent.setId("card");

        Label nameField = new Label();
        nameField.setText("Nom de l'event");

        Label priceField = new Label();
        priceField.setText("Prix");

        Label locationField = new Label();
        locationField.setText("Emplacement");

        Label typeField = new Label();
        typeField.setText("Type");

        newEvent.getChildren().addAll(nameField, priceField, locationField, typeField);
        eventContainer.getChildren().add(newEvent);
    }

    @FXML
    private void logout(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent homepage = FXMLLoader.load(
                Objects.requireNonNull(getClass().getResource("/com/example/backlogtp/connection_form.fxml"))
        );
        stage.setScene(new Scene(homepage));
        stage.setMaximized(true);
        stage.show();
    }

}
