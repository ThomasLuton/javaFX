package com.example.backlogtp.ui;

import com.example.backlogtp.PlannerApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import logic.dtos.CreateCategory;
import logic.dtos.CreateEvent;
import logic.dtos.UserInfo;
import logic.services.EventService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

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

}
