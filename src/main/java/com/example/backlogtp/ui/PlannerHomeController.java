package com.example.backlogtp.ui;

import com.example.backlogtp.PlannerApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import logic.dtos.CreateCategory;
import logic.dtos.CreateEvent;
import logic.dtos.UserInfo;
import logic.entities.Event;
import logic.services.EventService;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PlannerHomeController {

    private final EventService eventService = new EventService();

    @FXML
    private ToggleGroup eventType;

    @FXML
    private TextField eventNameField;

    @FXML
    private DatePicker eventDatePicker;

    @FXML
    private TextField eventLocationField;
    
   

    public void checkSelected() {
        RadioButton selected = (RadioButton) eventType.getSelectedToggle();
        if (selected != null) {
            System.out.println("Choix : " + selected.getText());
        } else {
            System.out.println("Aucun choix sélectionné");
        }
    }

    @FXML
    private void createEvent() {
        try {
            List<CreateCategory> categories = new ArrayList<>();
            categoriesContainer.getChildren().stream().filter(child -> child instanceof HBox).forEach( hbox -> {

                TextField nameField = (TextField) ((HBox) hbox).getChildren().stream()
                        .filter(v -> v.getId().equals("name"))
                        .findFirst().get();
                String name = nameField.getText();
                TextField priceField = (TextField) ((HBox) hbox).getChildren().stream()
                        .filter(v -> v.getId().equals("price"))
                        .findFirst().get();
                Integer price = Integer.valueOf(priceField.getText());
                TextField capacityField = (TextField) ((HBox) hbox).getChildren().stream()
                        .filter(v -> v.getId().equals("capacity"))
                        .findFirst().get();
                Integer capacity = Integer.valueOf(capacityField.getText());
                CreateCategory category = new CreateCategory(name, price, capacity);
                categories.add(category);
            });
            RadioButton selectedRadio = (RadioButton) eventType.getSelectedToggle();
            CreateEvent input = new CreateEvent(
                    PlannerApplication.staticUserInfo,
                    eventNameField.getText(),
                    LocalDateTime.of(eventDatePicker.getValue(), LocalTime.of(20, 00)),
                    eventLocationField.getText(),
                    selectedRadio.getText(),
                    categories
            );

            eventService.createEvent(input);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    private VBox categoriesContainer;
    
    private HBox createEventCard(Event event) {
        HBox card = new HBox(20);
        card.setId("card"); // lié au CSS components.css
        card.setMaxWidth(600);

        Label name = new Label(event.getName());
        Label date = new Label(event.getDate().toString());
        Label location = new Label(event.getLocation());
        Label type = new Label(event.getType());

        card.getChildren().addAll(name, date, location, type);
        return card;
    }


    @FXML
    private void addCategory() {
        HBox newCategory = new HBox(5);
        newCategory.setMaxWidth(500);

        TextField categoryField = new TextField();
        categoryField.setPromptText("Nom catégorie");
        categoryField.setId("name");

        TextField priceField = new TextField();
        priceField.setPromptText("Prix");
        priceField.setId("price");

        TextField seatAmountField = new TextField();
        seatAmountField.setPromptText("Nombre de places");
        seatAmountField.setId("capacity");


        Button removeBtn = new Button("X");
        removeBtn.setOnAction(e -> categoriesContainer.getChildren().remove(newCategory));

        newCategory.getChildren().addAll(categoryField, priceField, seatAmountField, removeBtn);
        categoriesContainer.getChildren().add(newCategory);
    }
    
    @FXML
    private VBox eventContainer;
    
    private void initialize() {
        try {
            // style des cards
            if (eventContainer != null) {
                eventContainer.getStylesheets().add("components.css");
            }

            // récupérer l'utilisateur connecté
            UserInfo currentUser = PlannerApplication.staticUserInfo;
            if (currentUser == null) {
                System.out.println("Aucun utilisateur connecté");
                return;
            }

            // récupérer ses événements
            var myEvents = eventService.listEventsForOrganizer(currentUser);

            // pour chaque event, créer une "card" HBox
            for (Event e : myEvents) {
                HBox card = new HBox(10);
                card.setId("card");
                card.setMaxWidth(600);

                Label nameLabel = new Label(e.getName());
                Label dateLabel = new Label(e.getDate().toString());
                Label locationLabel = new Label(e.getLocation());
                Label typeLabel = new Label(e.getType());

                card.getChildren().addAll(nameLabel, dateLabel, locationLabel, typeLabel);

                eventContainer.getChildren().add(card);
            }

        } catch (Exception e) {
            e.printStackTrace();
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
