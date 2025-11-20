package com.example.backlogtp.logic.entities;

import com.example.backlogtp.repositories.ReservationRepository;
import com.example.backlogtp.utils.AbstractDAO;
import com.example.backlogtp.utils.ReservationStatus;
import com.example.backlogtp.utils.exceptions.AnnulationTardiveException;
import com.example.backlogtp.logic.services.Payable;
import com.example.backlogtp.logic.services.Reservable;

import java.sql.SQLException;
import java.time.LocalDateTime;

public class Reservation extends AbstractDAO implements Payable, Reservable {

    private User user;
    private EventCategory event;
    private LocalDateTime reservationDate;

    private ReservationStatus status;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public EventCategory getEvent() {
        return event;
    }

    public void setEvent(EventCategory event) {
        this.event = event;
    }

    public LocalDateTime getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(LocalDateTime reservationDate) {
        this.reservationDate = reservationDate;
    }

    public ReservationStatus getStatus() {
        return status;
    }

    public void setStatus(ReservationStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "user=" + user +
                ", event=" + event +
                ", reservationDate=" + reservationDate +
                ", status='" + status + '\'' +
                '}';
    }

    @Override
    public boolean isBilled() {
        return this.status.equals(ReservationStatus.PAID);
    }

    @Override
    public void bill(Long cardNumber, String nameOnCard) {
        if(cardNumber.longValue() < 1000000000000000L){
            throw new RuntimeException("Card number invalid");
        }
        if(!nameOnCard.equals(this.user.getName())){
            throw new RuntimeException("Name invalid");
        }
        try {
            new ReservationRepository().pay(this);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void book() throws SQLException {
        new ReservationRepository().createReservation(this);
    }

    @Override
    public boolean isBookable() throws SQLException {
        int totalOfReservation = new ReservationRepository().countReservationFromOneEventCategory(event);
        return event.getCapacity() - totalOfReservation > 0;
    }

    @Override
    public void cancel() throws SQLException {
        if(event.getEvent().getDate().minusDays(1L).isBefore(LocalDateTime.now())){
            throw new AnnulationTardiveException("On ne peut pas annuler une réservation moins de 24h avant l'évenement");
        }
        new ReservationRepository().delete(this);
    }
}
