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
    
    public List<Event> findUpcomingEventsSortedByDate() throws SQLException {
    	 String query = """
    	            SELECT
    	                e.id       AS event_id,
    	                e.name     AS event_name,
    	                e.date     AS event_date,
    	                e.location AS event_location,
    	                e.type     AS event_type,
    	                u.id       AS user_id,
    	                u.name     AS user_name,
    	                u.email    AS user_email
    	            FROM events e
    	            JOIN users u ON e.organizer_id = u.id
    	            WHERE e.date >= NOW()
    	            ORDER BY e.date ASC
    	            """;
    	 PreparedStatement preparedStatement = connection.prepareStatement(query);
    	 ResultSet rs = preparedStatement.executeQuery();
    	 
    	 List<Event> events = new ArrayList<>();
    	 
    	 while (rs.next()) {

             String type = rs.getString("event_type");
             Event event;
             switch (type.toUpperCase()) {
                 case "CONCERT" -> event = new Concert();
                 case "SPECTACLE" -> event = new Spectacle();
                 case "CONFERENCE" -> event = new Conference();
                 default -> throw new ValidationException("Unknown event : " + type);
             }
             
             event.setId(rs.getLong("event_id"));
             event.setName(rs.getString("event_name"));
             event.setDate(rs.getTimestamp("event_date").toLocalDateTime());
             event.setLocation(rs.getString("event_location"));
             event.setType(type);
             
             // Organisateur 
             EventPlanner eventPlanner = new EventPlanner();
             eventPlanner.setId(rs.getLong("user_id"));
             eventPlanner.setName(rs.getString("user_name"));
             eventPlanner.setEmail(rs.getString("user_email"));
            
             // Associer l'event planner à l'évenement
             event.setUser(eventPlanner);
             
             events.add(event); //ajout à la liste
       
    	 }
    	 
    	 return events;
    }
}