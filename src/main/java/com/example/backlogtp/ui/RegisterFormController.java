package com.example.backlogtp.ui;

import com.example.backlogtp.utils.exceptions.ValidationException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import logic.services.UserService;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;
import java.util.Objects;

public class RegisterFormController {

    @FXML
    private TextField nameField;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField pwField;

    @FXML
    private Text customText;

    @FXML
    private CheckBox isEventPlanner;

    @FXML
    private void connect(ActionEvent event){
        try {
            Parent loginPage = FXMLLoader.load(
                    Objects.requireNonNull(getClass().getResource("/com/example/backlogtp/connection_form.fxml"))
            );
            Scene scene = new Scene(loginPage);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setMaximized(true);
            stage.show();
        }catch (Exception e){

        }
    }
    @FXML
    private void register(ActionEvent event) {
        try {
            new UserService().createUser(
                    nameField.getText(),
                    emailField.getText(),
                    pwField.getText(),
                    isEventPlanner.isSelected()
            );

            // Redirect to login page
            Parent loginPage = FXMLLoader.load(
                    Objects.requireNonNull(getClass().getResource("/com/example/backlogtp/connection_form.fxml"))
            );
            Scene scene = new Scene(loginPage);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setMaximized(true);
            stage.show();

        } catch (SQLException | ValidationException ex) {
            customText.setFill(Color.FIREBRICK);
            customText.setText(ex.getMessage());

        } catch (IOException ex) {
            customText.setFill(Color.FIREBRICK);
            customText.setText("Could not load login page.");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
    }
}
