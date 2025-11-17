package com.example.backlogtp.ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class PlannerHomeController {

    @FXML
    private ToggleGroup eventType;

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
        System.out.println("Create Event");
    }

    @FXML
    private VBox categoriesContainer;

    @FXML
    private void addCategory() {
        HBox newCategory = new HBox(5);
        newCategory.setMaxWidth(500);

        TextField categoryField = new TextField();
        categoryField.setPromptText("Nom catégorie");

        TextField priceField = new TextField();
        priceField.setPromptText("Prix");

        TextField seatAmountField = new TextField();
        seatAmountField.setPromptText("Nombre de places");

        Button removeBtn = new Button("X");
        removeBtn.setOnAction(e -> categoriesContainer.getChildren().remove(newCategory));

        newCategory.getChildren().addAll(categoryField, priceField, seatAmountField, removeBtn);
        categoriesContainer.getChildren().add(newCategory);
    }

    private void removeCategory(ActionEvent event) {
        event.getSource();
    }


}
