package logic.services;

import com.example.backlogtp.repositories.ReservationRepository;
import com.example.backlogtp.repositories.UserRepository;
import com.example.backlogtp.utils.exceptions.PlacesInsuffisantesException;
import logic.dtos.UserInfo;
import logic.entities.EventCategory;
import logic.entities.Reservation;

import java.sql.SQLException;
import java.time.LocalDateTime;

/**
 * Classe responsable de la logique métier liée aux réservations
 * 
 * Elle s'occupe principalement de : 
 * 
 * - renvoyer le nombre de places disponibles
 * - la création d'une nouvelle reservation.
 *
 */

public class ReservationService {

    private final ReservationRepository reservations = new ReservationRepository();
    private final UserRepository users = new UserRepository();
    
    /*
     * Calcule le nombre de places disponibles pour une catégorie donnée.
     */
    public int availablePlace(EventCategory eventCategory) throws SQLException {
        return eventCategory.getCapacity() - reservations.countReservationFromOneEventCategory(eventCategory);
    }

    /*
     * Créer une réservation pour un utilisateur et une catégorie donnée.
     * 
     */
    public void createReservation(UserInfo user, EventCategory category) throws SQLException {
        Reservation reservation = new Reservation();
        reservation.setReservationDate(LocalDateTime.now());
        reservation.setStatus("PENDING");
        reservation.setUser(users.findByEmail(user.email()));
        if(availablePlace(category) == 0){
            throw new PlacesInsuffisantesException();
        }
        reservation.setEvent(category);
        category.book(reservation);
        reservations.createReservation(reservation);
    }
}
