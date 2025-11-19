package logic.services;

import com.example.backlogtp.repositories.ReservationRepository;
import com.example.backlogtp.repositories.UserRepository;
import com.example.backlogtp.utils.exceptions.PlacesInsuffisantesException;
import logic.dtos.UserInfo;
import logic.entities.EventCategory;
import logic.entities.Reservation;

import java.sql.SQLException;
import java.time.LocalDateTime;

public class ReservationService {

    private final ReservationRepository reservations = new ReservationRepository();
    private final UserRepository users = new UserRepository();
    public int availablePlace(EventCategory eventCategory) throws SQLException {
        return eventCategory.getCapacity() - reservations.countReservationFromOneEventCategory(eventCategory);
    }

    public void createReservation(UserInfo user, EventCategory category) throws SQLException {
        Reservation reservation = new Reservation();
        reservation.setReservationDate(LocalDateTime.now());
        reservation.setStatus("PENDING");
        reservation.setUser(users.findByEmail(user.email()));
        if(availablePlace(category) == 0){
            throw new PlacesInsuffisantesException();
        }
        reservation.setEvent(category);
        reservation.book();
    }
}
