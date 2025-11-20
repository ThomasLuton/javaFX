package com.example.backlogtp.ui;

import com.example.backlogtp.PlannerApplication;
import com.example.backlogtp.utils.UserStatus;
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
import com.example.backlogtp.logic.dtos.UserInfo;
import com.example.backlogtp.logic.services.UserService;

import java.io.IOException;
import java.util.Objects;

public class ConnectionFormController {

    @FXML
    private TextField emailTextField;

    @FXML
    private PasswordField pwBox;

    @FXML
    private Text customText;

    /**
     * Vérifie les credentials de l'utilisateur pour lui permettre d'accéder a la homepage correspondant a son status
     * (client ou event planner)
     * @param event récupère la source de l'evenement
     * @throws Exception
     */
    @FXML
    private void login(ActionEvent event) throws Exception {
        try {
            UserInfo userInfo = new UserService().connect(emailTextField.getText(), pwBox.getText());
            PlannerApplication.staticUserInfo = userInfo;

            // Redirection vers la home sur le submit du bouton login en fonction du type d'user
            if(userInfo.type().equals(UserStatus.EVENTPLANNER)) {

                refreshPage(event, "/com/example/backlogtp/homepage_planner.fxml");

            } else if(userInfo.type().equals(UserStatus.CLIENT)) {

                refreshPage(event, "/com/example/backlogtp/marketplace_customer.fxml");
            }


        } catch (ValidationException | IOException ex) {
            customText.setFill(Color.FIREBRICK);
            customText.setText(ex.getMessage());
        }
    }


    /**
     * Redirige vers le formulaire de création de compte
     * @param event
     */
    @FXML
    private void register(ActionEvent event){
        try {
            refreshPage(event,"/com/example/backlogtp/register_form.fxml");
        } catch (Exception e) {}
    }


    /**
     * méthode générique pour rediriger vers une page donnée
     * @param event récupère la source du déclenchement
     * @param url donne le chemin relatif du fichier a charger sous forme de String
     * @throws IOException
     */
    private void refreshPage(ActionEvent event, String url) throws IOException {
        Parent registerPage = FXMLLoader.load(
                Objects.requireNonNull(getClass().getResource(url))
        );

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(registerPage));
        stage.setMaximized(true);
        stage.show();
    }
}
