package com.example.backlogtp.ui;

import com.example.backlogtp.utils.exceptions.ValidationException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class ConnectionFormController {

    @FXML
    private TextField emailTextField;

    @FXML
    private PasswordField pwBox;

    @FXML
    private Text customText;


    @FXML
    private void login(ActionEvent event) {

        try {
            // Redirection vers la home sur le submit du bouton login
            Parent homepage = FXMLLoader.load(
                    Objects.requireNonNull(getClass().getResource("/com/example/backlogtp/homepage.fxml"))
            );
            Scene homeScene = new Scene(homepage);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            stage.setScene(homeScene);
            stage.show();

            // TODO: implémenter les validations utilisateur

        } catch (ValidationException | IOException ex) {
            customText.setFill(Color.FIREBRICK);
            customText.setText(ex.getMessage());
        }
    }

}
