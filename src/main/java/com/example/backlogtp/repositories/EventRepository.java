package com.example.backlogtp.repositories;

import com.example.backlogtp.utils.exceptions.ValidationException;
import logic.entities.*;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.TemporalField;
import java.util.ArrayList;
import java.util.List;

public class EventRepository {

    private final Connection connection = DataBaseConnection.getConnection();

    public Long createEvent(Event event) throws SQLException {
        String query = "INSERT INTO events (name, date, location, type, organizer_id) VALUES ( ?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, event.getName());
        preparedStatement.setTimestamp(2, Timestamp.valueOf(event.getDate()));
        preparedStatement.setString(3, event.getLocation());
        preparedStatement.setString(4, event.getType());
        preparedStatement.setLong(5, event.getUser().getId());
        int rows = preparedStatement.executeUpdate();
        if(rows > 0){
            return (long) preparedStatement.getMaxRows();
        }
        return null;
    }

    public Event findById(Long id) throws SQLException{
        String query = "SELECT * FROM events where id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setLong(1, id);
        ResultSet rs = preparedStatement.executeQuery();
        List<Event> events= new ArrayList<>();
        while (rs.next()){
            String type = rs.getString("type");
            Event event;
            switch (type.toUpperCase()) {
                case "CONCERT" -> event = new Concert();
                case "SPECTACLE" -> event = new Spectacle();
                case "CONFERENCE" -> event = new Conference();
                default -> throw new ValidationException("Unknown event : " + type);
            }
            event.setName(rs.getString("name"));
            event.setDate(rs.getTimestamp("date").toLocalDateTime());
            event.setLocation(rs.getString("location"));
            event.setId(rs.getLong("id"));
            events.add(event);
        }
        return events.size() == 0 ? null: events.get(0);
    }

    public void createEventCategory(EventCategory category) throws  SQLException{
        String query = "INSERT INTO event_categories (event_id, name, price, capacity) VALUES ( ?, ?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setLong(1, category.getEvent().getId());
        preparedStatement.setString(2, category.getName());
        preparedStatement.setDouble(3, category.getPrice());
        preparedStatement.setInt(4, category.getCapacity());
        preparedStatement.executeUpdate();
    }

    public Event findLast()  throws  SQLException{
        String query = "SELECT * FROM events ORDER BY id DESC LIMIT 1;";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet rs = preparedStatement.executeQuery();
        List<Event> events = new ArrayList<>();
        while (rs.next()){
            String type = rs.getString("type");
            Event event;
            switch (type.toUpperCase()) {
                case "CONCERT" -> event = new Concert();
                case "SPECTACLE" -> event = new Spectacle();
                case "CONFERENCE" -> event = new Conference();
                default -> throw new ValidationException("Unknown event : " + type);
            }
            event.setName(rs.getString("name"));
            event.setDate(rs.getTimestamp("date").toLocalDateTime());
            event.setLocation(rs.getString("location"));
            event.setId(rs.getLong("id"));
            events.add(event);
        }
        return events.size() == 0 ? null: events.get(0);
    }
}
