package com.example.backlogtp.ui;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class RegisterForm {

    public VBox getView() {
        Label title = new Label("Register");
        title.setId("");

        Button btnRegister = new Button("Register");

        btnRegister.setOnAction(e -> {
            System.out.println("clicked");
        });

        VBox vbox = new VBox();
        vbox.getChildren().addAll(title, btnRegister);

        return vbox;
    }
}
