package com.example.backlogtp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import com.example.backlogtp.logic.dtos.UserInfo;

import java.io.IOException;
import java.util.Objects;

public class PlannerApplication extends Application {

    public static UserInfo staticUserInfo = null;
    @Override
    public void start(Stage primaryStage) throws IOException {
        //String resource = "register_form.fxml";
        //String resource = "marketplace_customer.fxml";
        String resource = "homepage_customer.fxml";
        staticUserInfo = new UserInfo("client", "client@", "CLIENT", 2L);

        Parent registry = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(resource)));
        Scene scene = new Scene(registry);

        // Fullscreen
        primaryStage.setMaximized(true);

        primaryStage.setTitle("Event Planner");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}