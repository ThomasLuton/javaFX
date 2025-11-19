package com.example.backlogtp.ui;

import com.example.backlogtp.PlannerApplication;
import com.example.backlogtp.repositories.DataBaseConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import logic.entities.Event;
import logic.entities.EventCategory;
import logic.entities.EventPlanner;
import logic.services.EventService;
import logic.services.ReservationService;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CustomerMarketController {

    @FXML
    public Text customText;

    @FXML
    private VBox eventContainer;

    @FXML
    private VBox selectedEventsList;

    @FXML
    public BorderPane root;

    private final EventService eventService = new EventService();
    private final ReservationService reservationService = new ReservationService();
    private final List<EventCategory> selectedCategories = new ArrayList<>();

    @FXML
    private void initialize() throws SQLException {
        eventContainer.getStylesheets().add("components.css");

        // nom, date, location, organisateur, categories (-> nom, prix et capacité)
        List<Event> events = eventService.listUpcomingEventsForClient();

        if(events.isEmpty()){
            customText.setText("No events found");
        }
        customText.setText("Tous les événements");

        for (Event event : events) {

            // Card linéaire pour les informations générales des events
            GridPane newEvent = new GridPane();
            newEvent.setId("card");
            newEvent.setHgap(10);
            newEvent.setVgap(20);
            ColumnConstraints name = new ColumnConstraints(200);
            ColumnConstraints date = new ColumnConstraints(120);
            ColumnConstraints location = new ColumnConstraints(150);
            ColumnConstraints type = new ColumnConstraints();
            newEvent.getColumnConstraints().addAll(name, date, location, type);

            // Affectation des noms de chaque champs
            Label nameField = new Label(event.getName());
            Label dateField = new Label("Date: " + event.getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            Label locationField = new Label("À: " + event.getLocation());
            Label typeField = new Label(event.getType());

            // Affectation des positions sur la grille de chaque champs
            newEvent.add(nameField,     0, 0);
            newEvent.add(dateField,     1, 0);
            newEvent.add(locationField, 2, 0);
            newEvent.add(typeField,     3, 0);


            // Sous card linéaire pour les informations spécifiques des events
            GridPane newEventCategories = new GridPane();
            newEventCategories.setId("categories-card");
            ColumnConstraints categories = new ColumnConstraints(50);
            ColumnConstraints price = new ColumnConstraints(50);
            ColumnConstraints quantity = new ColumnConstraints(50);
            newEventCategories.getColumnConstraints().addAll(categories, price, quantity);

            for (int i = 0; i < event.getCategories().size(); i++) {
                EventCategory eventCategory = event.getCategories().get(i);

                Label category = new Label(eventCategory.getName());
                Label categoryPrice = new Label(String.valueOf(eventCategory.getPrice()) + "€");
                int qtt = reservationService.availablePlace(eventCategory);
                Label categoryQuantity = new Label(qtt + "/" + eventCategory.getCapacity() + " places");

                Button addBtn = new Button("+");
                addBtn.setId("addBtn");
                addBtn.setOnAction(e -> addSelectedEvent(event, eventCategory));

                newEvent.add(category,0,i+1);
                newEvent.add(categoryPrice,1,i+1);
                newEvent.add(categoryQuantity,2,i+1);
                newEvent.add(addBtn,3,i+1);
            }


            eventContainer.getChildren().addAll(newEvent, newEventCategories);
        }
    }

    @FXML
    private void logout(ActionEvent event) throws IOException {
        PlannerApplication.staticUserInfo = null;
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent homepage = FXMLLoader.load(
                Objects.requireNonNull(getClass().getResource("/com/example/backlogtp/connection_form.fxml"))
        );
        stage.setScene(new Scene(homepage));
        stage.setMaximized(true);
        stage.show();
    }

    public void addSelectedEvent(Event event, EventCategory eventCategory) {
        selectedCategories.add(eventCategory);
        selectedEventsList.getStylesheets().add("components.css");

        GridPane newCard = new GridPane();
        newCard.getStyleClass().add("side-card");

        ColumnConstraints nameCol = new ColumnConstraints(150);
        ColumnConstraints categoryCol = new ColumnConstraints();
        categoryCol.setHgrow(Priority.ALWAYS);
        ColumnConstraints buttonCol = new ColumnConstraints();
        buttonCol.setMinWidth(Region.USE_PREF_SIZE);
        buttonCol.setHalignment(HPos.RIGHT);

        newCard.getColumnConstraints().addAll(nameCol, categoryCol, buttonCol);

        Label eventName = new Label(event.getName());
        Label categoryName = new Label(eventCategory.getName());
        Button removeBtn = new Button("X");
        removeBtn.setId("addBtn");
        removeBtn.setOnAction(e -> {
                    selectedEventsList.getChildren().remove(newCard);
                    selectedCategories.remove(eventCategory);
                });

        newCard.add(eventName,     0, 0);
        newCard.add(categoryName,  1, 0);
        newCard.add(removeBtn,     2, 0);

        selectedEventsList.getChildren().add(newCard);
    }

    @FXML
    public void validateReservations(){
        selectedCategories.forEach(eventCategory -> {
            try {
                reservationService.createReservation(PlannerApplication.staticUserInfo, eventCategory);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setContentText("Place(s) réservée(s) avec succès");
                alert.showAndWait();

                Parent refreshed = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/backlogtp/marketplace_customer.fxml")));
                Stage stage = (Stage) root.getScene().getWindow();
                stage.setScene(new Scene(refreshed));
                stage.setMaximized(true);
                stage.show();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
