package com.example.backlogtp.repositories;

import logic.entities.Event;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.ZoneOffset;
import java.time.temporal.TemporalField;

public class EventRepository {

    private final Connection connection = DataBaseConnection.getConnection();

    public void createEvent(Event event) throws SQLException {
        String query = "INSERT INTO events (name, date, location, type, organizer_id) VALUES ( ?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, event.getName());
        preparedStatement.setTimestamp(2, Timestamp.valueOf(event.getDate()));
        preparedStatement.setString(3, event.getLocation());
        preparedStatement.setString(4, event.getType());
        preparedStatement.setLong(5, event.getUser().getId());
        preparedStatement.execute();
    }
}
