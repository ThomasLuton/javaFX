package com.example.backlogtp.repositories;

import logic.entities.Event;
import logic.entities.EventCategory;
import logic.entities.Reservation;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ReservationRepository {
    private final Connection connection = DataBaseConnection.getConnection();
    private final UserRepository users = new UserRepository();

    public List<Reservation> findAllByEvent(Event event) throws SQLException{
        List<Reservation> reservations = new ArrayList<>();
        event.getCategories().forEach(eventCategory -> {
            try {
                reservations.addAll(findAllByEventCategory(eventCategory));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
        return reservations;
    }

    public List<Reservation> findAllByEventCategory(EventCategory eventCategory) throws SQLException{
        String query = "SELECT * FROM reservations WHERE event_category_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setLong(1, eventCategory.getId());
        ResultSet rs = preparedStatement.executeQuery();
        List<Reservation> reservations = new ArrayList<>();
        while (rs.next()){
            Reservation reservation = new Reservation();
            reservation.setId(rs.getLong("id"));
            reservation.setUser(users.findById(rs.getLong("user_id")));
            reservation.setEvent(eventCategory);
            reservation.setStatus(rs.getString("status"));
            reservation.setReservationDate(rs.getTimestamp("reservation_date").toLocalDateTime());
            reservations.add(reservation);
        }
        return reservations;
    }

    public int countReservationFromOneEventCategory(EventCategory eventCategory) throws SQLException{
        String query = "SELECT count(r.id) FROM reservations r where r.event_category_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setLong(1, eventCategory.getId());
        ResultSet rs = preparedStatement.executeQuery();
        int count = 0;
        while (rs.next()){
            count = rs.getInt(1);
        }
        return count;
    }

    public void createReservation(Reservation reservation) throws SQLException{
        String query = "INSERT INTO reservations(user_id, event_category_id, reservation_date, status) VALUES ( ?, ?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setLong(1, reservation.getUser().getId());
        preparedStatement.setLong(2, reservation.getEvent().getId());
        preparedStatement.setTimestamp(3, Timestamp.valueOf(reservation.getReservationDate()));
        preparedStatement.setString(4, reservation.getStatus());
        preparedStatement.executeUpdate();
    }
}
