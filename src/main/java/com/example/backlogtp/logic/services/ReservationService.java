package com.example.backlogtp.logic.services;

import com.example.backlogtp.repositories.ReservationRepository;
import com.example.backlogtp.repositories.UserRepository;
import com.example.backlogtp.utils.ReservationStatus;
import com.example.backlogtp.utils.exceptions.PlacesInsuffisantesException;
import com.example.backlogtp.logic.dtos.UserInfo;
import com.example.backlogtp.logic.entities.EventCategory;
import com.example.backlogtp.logic.entities.Reservation;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public class ReservationService {

    private final ReservationRepository reservations = new ReservationRepository();
    private final UserRepository users = new UserRepository();
    public int availablePlace(EventCategory eventCategory) throws SQLException {
        return eventCategory.getCapacity() - reservations.countReservationFromOneEventCategory(eventCategory);
    }

    public int soldPlace(EventCategory eventCategory) throws SQLException{
        return reservations.countReservationFromOneEventCategory(eventCategory);
    }

    public void createReservation(UserInfo user, EventCategory category) throws SQLException {
        Reservation reservation = new Reservation();
        reservation.setReservationDate(LocalDateTime.now());
        reservation.setStatus(ReservationStatus.PENDING);
        reservation.setUser(users.findByEmail(user.email()));
        if(availablePlace(category) == 0){
            throw new PlacesInsuffisantesException();
        }
        reservation.setEvent(category);
        reservation.book();
    }

    public List<Reservation> findAllFromOneUser(Long userId) throws SQLException {
        return reservations.findAllByUserId(userId);
    }
}
