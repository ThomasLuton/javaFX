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
import com.example.backlogtp.logic.services.UserService;

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


    /**
     * Redirige vers la page de login
     * @param event
     */
    @FXML
    private void connect(ActionEvent event){
        try {
            refreshPage(event, "/com/example/backlogtp/connection_form.fxml");

        } catch (Exception e) {
            System.out.println(e.getMessage());

        }
    }

    /**
     * créé un utilisateur et le persiste en base de données à partir des champs du formulaire
     * @param event récupère la source de l'evenement
     */
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
            refreshPage(event,"/com/example/backlogtp/connection_form.fxml");

        } catch (SQLException | ValidationException ex) {
            customText.setFill(Color.FIREBRICK);
            customText.setText(ex.getMessage());

        } catch (IOException ex) {
            customText.setFill(Color.FIREBRICK);
            customText.setText("Could not load login page.");

        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * méthode générique pour rediriger vers une page donnée
     * @param event récupère la source du déclenchement
     * @param url donne le chemin relatif du fichier a charger sous forme de String
     * @throws IOException
     */
    private void refreshPage(ActionEvent event, String url) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent homepage = FXMLLoader.load(
                Objects.requireNonNull(getClass().getResource(url))
        );
        stage.setScene(new Scene(homepage));
        stage.setMaximized(true);
        stage.show();
    }
}
