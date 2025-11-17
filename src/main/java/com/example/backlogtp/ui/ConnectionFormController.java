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
import logic.dtos.UserInfo;
import logic.services.UserService;

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
    private void login(ActionEvent event) throws Exception {
        try {
            UserInfo userInfo = new UserService().connect(emailTextField.getText(), pwBox.getText());
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Redirection vers la home sur le submit du bouton login en fonction du type d'user
            if(userInfo.type().equals("eventPlanner")) {
                Parent homepage = FXMLLoader.load(
                        Objects.requireNonNull(getClass().getResource("/com/example/backlogtp/homepage_planner.fxml"))
                );
                stage.setScene(new Scene(homepage));
                stage.setMaximized(true);

            } else if(userInfo.type().equals("client")) {
                Parent homepage = FXMLLoader.load(
                        Objects.requireNonNull(getClass().getResource("/com/example/backlogtp/homepage_customer.fxml"))
                );
                stage.setScene(new Scene(homepage));
                stage.setMaximized(true);
            }
            stage.show();

            // TODO: implémenter les validations utilisateur

        } catch (ValidationException | IOException ex) {
            customText.setFill(Color.FIREBRICK);
            customText.setText(ex.getMessage());
        }
    }

    @FXML
    private void register(ActionEvent event){
        try {
            Parent registerPage = FXMLLoader.load(
                    Objects.requireNonNull(getClass().getResource("/com/example/backlogtp/register_form.fxml"))
            );
            Scene scene = new Scene(registerPage);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setMaximized(true);
            stage.show();
        } catch (Exception e) {}
    }

}
