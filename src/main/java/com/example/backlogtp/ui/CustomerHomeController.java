package com.example.backlogtp.ui;

import com.example.backlogtp.PlannerApplication;
import com.example.backlogtp.utils.exceptions.AnnulationTardiveException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import com.example.backlogtp.logic.entities.Reservation;
import com.example.backlogtp.logic.services.ReservationService;

import java.io.IOException;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CustomerHomeController {

    @FXML
    private VBox eventContainer;

    private final List<Reservation> reservations = new ArrayList<>();
    private final ReservationService reservationService = new ReservationService();

    @FXML
    private void initialize() throws SQLException {
        reservations.addAll(reservationService.findAllFromOneUser(PlannerApplication.staticUserInfo.id()));
        HBox newEvent =  new HBox(5);
        newEvent.setId("card");

        eventContainer.getChildren().add(newEvent);
        reservations.forEach(reservation -> addReservation(reservation));
    }

    private void addReservation(Reservation reservation){
        GridPane newCard = new GridPane();
        newCard.getStyleClass().add("side-card");
        newCard.setHgap(10);

        ColumnConstraints nameCol = new ColumnConstraints(100);
        ColumnConstraints categoryCol = new ColumnConstraints(100);
        categoryCol.setHgrow(Priority.ALWAYS);
        ColumnConstraints buttonCol = new ColumnConstraints();
        buttonCol.setMinWidth(Region.USE_PREF_SIZE);
        buttonCol.setHalignment(HPos.RIGHT);

        newCard.getColumnConstraints().addAll(nameCol, categoryCol, buttonCol);

        Label eventName = new Label(reservation.getEvent().getEvent().getName());
        Label categoryName = new Label(reservation.getEvent().getName());
        Label date = new Label(reservation.getEvent().getEvent().getDate().format(DateTimeFormatter.ofPattern("dd/MM:YYYY")));
        Label status = new Label(reservation.getStatus());
        Button bill = new Button("Payer");
        bill.getStyleClass().add("card-btn");
        bill.setOnMouseClicked(mouseEvent -> {
            openPaiementScreen(reservation);
        });
        Button cancel = new Button("Annuler");
        cancel.getStyleClass().add("card-btn");
        cancel.setOnAction(mouseEvent -> {
            try {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setHeaderText("Annuler la reservation ?");
                alert.setContentText("Voulez vous vraiment annuler la reservation?");
                alert.showAndWait();

                if(alert.getResult() == ButtonType.OK){
                    reservation.cancel();
                    reservations.remove(reservation);
                    reloadPage(mouseEvent);
                }
            } catch (AnnulationTardiveException | SQLException e) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(e.getClass().getSimpleName());
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        newCard.add(eventName,     0, 0);
        newCard.add(categoryName,  1, 0);
        newCard.add(date, 2, 0);
        newCard.add(status, 3, 0);
        newCard.add(bill, 4,0);
        newCard.add(cancel, 5,0);

        eventContainer.getChildren().add(newCard);
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

    @FXML
    private void goToMarket(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent homepage = FXMLLoader.load(
                Objects.requireNonNull(getClass().getResource("/com/example/backlogtp/marketPlace_customer.fxml"))
        );
        stage.setScene(new Scene(homepage));
        stage.setMaximized(true);
        stage.show();
    }

    private void openPaiementScreen(Reservation reservation){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("File ton fric, chacal");
        alert.setContentText("Non je déconne j'ai rien implémenter pour l'instant");
        alert.showAndWait();
    }

    private void reloadPage(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent homepage = FXMLLoader.load(
                Objects.requireNonNull(getClass().getResource("/com/example/backlogtp/homepage_customer.fxml"))
        );
        stage.setScene(new Scene(homepage));
        stage.setMaximized(true);
        stage.show();
    }

}
