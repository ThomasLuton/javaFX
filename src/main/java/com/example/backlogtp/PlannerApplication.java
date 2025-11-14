package com.example.backlogtp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class PlannerApplication extends Application {
    @Override
    public void start(Stage primaryStage) throws IOException {

        Parent registry = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("register_form.fxml")));
        Scene scene = new Scene(registry);

        primaryStage.setTitle("Event Planner");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}