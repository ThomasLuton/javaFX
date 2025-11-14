package com.example.backlogtp;

import com.example.backlogtp.ui.RegisterForm;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class PlannerApplication extends Application {
    @Override
    public void start(Stage primaryStage) {
        RegisterForm form = new RegisterForm();

        Scene scene = new Scene(form.getView(), 800, 600);

        primaryStage.setTitle("App");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}