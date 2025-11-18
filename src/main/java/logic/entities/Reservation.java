package logic.entities;

import com.example.backlogtp.utils.AbstractDAO;
import logic.services.Payable;
import logic.services.Reservable;

import java.time.LocalDateTime;
import java.util.Objects;

public class Reservation extends AbstractDAO implements Payable {

    private User user;
    private EventCategory event;
    private LocalDateTime reservationDate;

    private String status;

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
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
        return false;
    }

    @Override
    public void bill() {

    }
}
