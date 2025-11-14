package com.example.backlogtp.ui;

import com.example.backlogtp.utils.exceptions.ValidationException;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import javafx.stage.PopupWindow;
import logic.services.UserService;

import java.sql.SQLException;

public class RegisterForm {

    public VBox getView() {
        Label title = new Label("Register");

        Button btnRegister = new Button("Register");
        btnRegister.setOnAction(e -> {System.out.println("clicked");});

        VBox vbox = new VBox();
        vbox.getChildren().addAll(title, btnRegister);
        return vbox;
    }

    public Scene getFormScene() {
        /* Creation d'une grid centrée, 1Opx de gap vertical et horizontal
         * entre chaque élément, padding de 25px sur toute la grid
         */
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        /* Creation de tous les champs du formulaire */
        Text title = new Text("Register");
        title.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        // i = column, i1 = row
        grid.add(title, 0, 0, 2, 1);

        Label userName = new Label("Name:");
        grid.add(userName, 0, 1);
        TextField userTextField = new TextField();
        grid.add(userTextField, 1, 1);

        Label email = new Label("Email:");
        grid.add(email, 0, 2);
        TextField emailTextField = new TextField();
        grid.add(emailTextField, 1, 2);

        Label pw = new Label("Password:");
        grid.add(pw, 0, 3);
        PasswordField pwBox = new PasswordField();
        grid.add(pwBox, 1, 3);


        /* Bouton de submit, sur pression : validation des champs, puis récupération des données dans
         * les champs name, email et password si corrects.
         */
        Button btnRegister = new Button("Register");
        HBox btnBox = new HBox(10);
        btnBox.setAlignment(Pos.BOTTOM_RIGHT);
        btnBox.getChildren().add(btnRegister);
        grid.add(btnBox, 1, 5);

        // Texte custom sur press du bouton
        final Text customText = new Text();
        grid.add(customText, 1, 4);

        btnRegister.setOnAction(e -> {
            try {
                new UserService().createUser(userTextField.getText(), emailTextField.getText(),pwBox.getText(), false);

                //go to connect
            } catch (SQLException ex) {
                customText.setFill(Color.FIREBRICK);
                customText.setText(ex.getMessage());
            } catch (ValidationException ex){
                customText.setFill(Color.FIREBRICK);
                customText.setText(ex.getMessage());
            }
        });

        return new Scene(grid, 300, 300);
    }
}
