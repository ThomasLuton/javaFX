package com.example.backlogtp.ui;

import com.example.backlogtp.PlannerApplication;
import com.example.backlogtp.logic.entities.EventCategory;
import com.example.backlogtp.repositories.ReservationRepository;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import com.example.backlogtp.logic.dtos.CreateCategory;
import com.example.backlogtp.logic.dtos.CreateEvent;
import com.example.backlogtp.logic.dtos.UserInfo;
import com.example.backlogtp.logic.entities.Event;
import com.example.backlogtp.logic.services.EventService;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PlannerHomeController {

    private final EventService eventService = new EventService();

    @FXML
    public BorderPane root;

    @FXML
    public Text eventPresenceText;

    @FXML
    public VBox incomeContainer;

    @FXML
    public Label totalIncome;

    @FXML
    private ToggleGroup eventType;

    @FXML
    private TextField eventNameField;

    @FXML
    private DatePicker eventDatePicker;

    @FXML
    private TextField eventLocationField;

    @FXML
    private VBox eventsContainer;

    @FXML
    private VBox categoriesContainer;

    @FXML
    private Text customText;

    @FXML
    private Text incomePresence;

    private ReservationRepository reservationRepository = new ReservationRepository();

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
                CreateCategory category = new CreateCategory(name, capacity, price);
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



            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText(null);
            alert.setContentText("Voulez vous créer cet évenement ?");
            alert.showAndWait();

            if (alert.getResult().equals(ButtonType.OK)){
                eventService.createEvent(input);
                Alert success = new Alert(Alert.AlertType.INFORMATION);
                success.setHeaderText(null);
                success.setContentText("Événement créé avec succès");
                success.showAndWait();
                Parent refreshed = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/backlogtp/homepage_planner.fxml")));
                Stage stage = (Stage) root.getScene().getWindow();
                stage.setScene(new Scene(refreshed));
                stage.setMaximized(true);
                stage.show();
            }
        } catch (Exception e){
            customText.setFill(Color.FIREBRICK);
            customText.setText(e.getMessage());
        }
    }
    
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
        removeBtn.getStyleClass().add("small-btn");
        removeBtn.setOnAction(e -> categoriesContainer.getChildren().remove(newCategory));

        newCategory.getChildren().addAll(categoryField, priceField, seatAmountField, removeBtn);
        categoriesContainer.getChildren().add(newCategory);
    }

    @FXML
    private void initialize() {
        try {
            if (eventsContainer != null) {
                eventsContainer.getStylesheets().add("components.css");
            }
            // récupérer l'utilisateur connecté
            UserInfo currentUser = PlannerApplication.staticUserInfo;
            if (currentUser == null) {
                System.out.println("Aucun utilisateur connecté");
                return;
            }

            // charger toutes les fonctionnalités
            addCategory();
            addPlannerEvents(currentUser);
            addIncomes(currentUser);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addPlannerEvents(UserInfo currentUser) throws Exception {

        // récupérer les événements du planner actuel
        List<Event> myEvents = eventService.listEventsForOrganizer(currentUser);

        if (myEvents.isEmpty()) {
            eventPresenceText.setText("Vous n'avez pas encore créé d'événements.");
        }

        // pour chaque event, créer une "card" HBox
        for (Event e : myEvents) {
            // Card linéaire pour les informations générales des events
            GridPane plannerCard = new GridPane();
            plannerCard.setId("card");
            plannerCard.setHgap(10);
            plannerCard.setVgap(20);
            ColumnConstraints name = new ColumnConstraints(160);
            ColumnConstraints date = new ColumnConstraints(160);
            plannerCard.getColumnConstraints().addAll(name, date);

            // Affectation des noms de chaque champs
            Label nameField = new Label(e.getName());
            Label dateField = new Label("Date: " + e.getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            Label locationField = new Label("À: " + e.getLocation());
            Label typeField = new Label("Type: " + e.getType());

            // Affectation des positions sur la grille de chaque champs
            plannerCard.add(nameField,     0, 0);
            plannerCard.add(typeField,     1, 0);
            plannerCard.add(locationField, 0, 1);
            plannerCard.add(dateField,     1, 1);

            eventsContainer.getChildren().add(plannerCard);
        }
    }

    private void addIncomes(UserInfo currentUser) throws Exception {
        List<Event> myEvents = eventService.listEventsForOrganizer(currentUser);
        if (myEvents.isEmpty()) {
            incomePresence.setText("Vous n'avez pas encore de revenus");
        }

        Double totalIncomes = 0.0;
        incomeContainer.setSpacing(30);

        // itérer sur chaque event pour calculer nb de place vendues et déduire la valeur générée
        for (Event e : myEvents) {
            GridPane eventIncomesBox = new GridPane();
            eventIncomesBox.setVgap(10);
            Label eventNameField = new Label(e.getName());
            eventNameField.getStyleClass().add("event-title");
            eventIncomesBox.add(eventNameField, 0, 0);

            Integer seatsSold = 0;
            Double incomes = 0.0;
            for (int i = 0; i < e.getCategories().size(); i++) {
                EventCategory cat = e.getCategories().get(i);

                GridPane categoryIncomeGrid = new GridPane();
                categoryIncomeGrid.setHgap(10);

                seatsSold += reservationRepository.countReservationFromOneEventCategory(cat);
                incomes += seatsSold * cat.getPrice();
                Label catNameField = new Label(cat.getName());
                Label seatsSoldField = new Label("Places vendues: " + seatsSold);
                Label incomesField = new Label("Revenus: " + incomes + "€");

                categoryIncomeGrid.add(catNameField, 0, 0);
                categoryIncomeGrid.add(seatsSoldField, 0, 1);
                categoryIncomeGrid.add(incomesField, 0, 2);

                eventIncomesBox.add(categoryIncomeGrid, 0, i+1);

                totalIncomes += incomes;
            }

            incomeContainer.getChildren().addAll(eventIncomesBox);
        }
        totalIncome.setText("Total: " + totalIncomes + "€");
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

}
