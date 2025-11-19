package com.example.backlogtp.logic.tests;

import com.example.backlogtp.repositories.EventRepository;
import com.example.backlogtp.repositories.ReservationRepository;
import com.example.backlogtp.repositories.UserRepository;
import com.example.backlogtp.logic.dtos.UserInfo;
import com.example.backlogtp.logic.entities.Event;
import com.example.backlogtp.logic.entities.EventCategory;
import com.example.backlogtp.logic.entities.Reservation;
import com.example.backlogtp.logic.entities.User;
import com.example.backlogtp.logic.services.ReservationService;

import java.sql.SQLException;
import java.util.List;

public class ThomasTest {
    public static void main(String[] args) throws SQLException {
        Event event = new EventRepository().findById(2L);
        EventCategory eventCategory = new EventRepository().findByEventId(event).get(0);

        User user = new UserRepository().findById(1L);
        UserInfo info = new UserInfo(user.getName(), user.getEmail(), user.getStatus(), user.getId());
        new ReservationService().createReservation(info, eventCategory);
        List<Reservation> reservations = new ReservationRepository().findAllByEvent(event);
        System.out.println(reservations.get(0));
        reservations.get(0).bill(4000500060007000L, "client");
        reservations = new ReservationRepository().findAllByEvent(event);
        System.out.println(reservations.get(0).isBilled());
    }
}
