package com.example.backlogtp.ui;

import com.example.backlogtp.repositories.DataBaseConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import logic.entities.Event;
import logic.services.EventService;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

public class CustomerMarketController {

    @FXML
    private VBox eventContainer;

    private final EventService eventService = new EventService();

    @FXML
    private void initialize() throws SQLException {
        eventContainer.getStylesheets().add("components.css");

        // TODO: UTILISER LA LISTE D'EVENT POUR AFFICHER LE MUR

        // nom, date, location, organisateur, categories (-> nom, prix et capacité)
        List<Event> events = eventService.listUpcomingEventsForClient();

        for (Event event : events) {

            HBox newEvent =  new HBox(5);
            newEvent.setMaxWidth(500);
            newEvent.setId("card");

            Label nameField = new Label();
            nameField.setText(event.getName());

            Label dateField = new Label();
            dateField.setText(event.getDate().format(DateTimeFormatter.ofPattern("dd/MM/YYYY")));

            Label locationField = new Label();
            locationField.setText(event.getLocation());

            Label typeField = new Label();
            typeField.setText(event.getType());

            Button addBtn = new Button("+");
            addBtn.setId("addBtn");
            addBtn.setOnAction(System.out::println);

            newEvent.getChildren().addAll(nameField, dateField, locationField, typeField, addBtn);
            eventContainer.getChildren().add(newEvent);

        }
    }

    @FXML
    private void logout(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent homepage = FXMLLoader.load(
                Objects.requireNonNull(getClass().getResource("/com/example/backlogtp/connection_form.fxml"))
        );
        stage.setScene(new Scene(homepage));
        stage.setMaximized(true);
        stage.show();
    }
}
