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
    private final UserRepository users = new UserRepository();

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
        String query = "SELECT * FROM events e where e.id = ?";
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
            event.setUser(users.findById(rs.getLong("organizer_id")));
            event.getCategories().addAll(findByEventId(event));
            events.add(event);
        }
        return events.size() == 0 ? null: events.get(0);
    }

    public List<EventCategory> findByEventId(Event event) throws SQLException {
        String query = "SELECT * FROM event_categories where event_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setLong(1, event.getId());
        ResultSet resultSet = preparedStatement.executeQuery();
        List<EventCategory> eventCategories = new ArrayList<>();
        while (resultSet.next()){
            EventCategory category = new EventCategory();
            category.setId(resultSet.getLong("id"));
            category.setPrice(resultSet.getDouble("price"));
            category.setCapacity(resultSet.getInt("capacity"));
            category.setName(resultSet.getString("name"));
            category.setEvent(event);
            eventCategories.add(category);
        }
        return eventCategories;
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
            event.setUser(users.findById(rs.getLong("organizer_id")));
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
             event.getCategories().addAll(findByEventId(event));
             
             events.add(event); //ajout à la liste
       
    	 }
    	 
    	 return events;
    }
    
    // Récupérer les événements crées par un organisateur en se basant sur son mail
    
    public List <Event> findEventsByOrganizerEmail(String email) throws SQLException {
    	
    	String query = """
    	        SELECT e.*
    	        FROM events e
    	        JOIN users u ON e.organizer_id = u.id
    	        WHERE u.email = ?
    	        ORDER BY e.date ASC
    	        """;
    	PreparedStatement preparedStatement = connection.prepareStatement(query);
    	preparedStatement.setString(1, email); 
    	ResultSet rs = preparedStatement.executeQuery();
    	
    	List <Event> events = new ArrayList<>();
    	
    	while (rs.next()) {
            String type = rs.getString("type");
            Event event;

            switch (type.toUpperCase()) {
                case "CONCERT" -> event = new Concert();
                case "SPECTACLE" -> event = new Spectacle();
                case "CONFERENCE" -> event = new Conference();
                default -> throw new ValidationException("Unknown event : " + type);
            }
            
        event.setId(rs.getLong("id"));
        event.setName(rs.getString("name"));
        event.setDate(rs.getTimestamp("date").toLocalDateTime());
        event.setLocation(rs.getString("location"));
        event.setType(type);
        
        // récupérer l'organisateur complet (User) 
        Long organizerId = rs.getLong("organizer_id");
        event.setUser(users.findById(organizerId));
        
        event.getCategories().addAll(findByEventId(event));
        
        events.add(event);
    }
    	
    	return events;
    }
    
    public List<Event> findUpcomingEventsFiltered(String typeFilter, String locationFilter, String artistFilter) 
            throws SQLException {

        StringBuilder sql = new StringBuilder("""
            SELECT 
                e.id AS event_id,
                e.name AS event_name,
                e.location AS event_location,
                e.date AS event_date,
                e.type AS event_type,
                u.id AS user_id,
                u.name AS user_name,
                u.email AS user_email
            FROM events e
            JOIN users u ON u.id = e.organizer_id
            WHERE e.date >= NOW()
        """);

        List<Object> params = new ArrayList<>();

        // -- Filtre par type
        if (typeFilter != null && !typeFilter.isBlank()) {
            sql.append(" AND e.type = ? ");
            params.add(typeFilter.toUpperCase());
        }

        // -- Filtre par lieu
        if (locationFilter != null && !locationFilter.isBlank()) {
            sql.append(" AND LOWER(e.location) LIKE LOWER(?) ");
            params.add("%" + locationFilter + "%");
        }

        // -- Filtre par nom de l'artiste / intervenant
        if (artistFilter != null && !artistFilter.isBlank()) {
            sql.append(" AND LOWER(e.name) LIKE LOWER(?) ");
            params.add("%" + artistFilter + "%");
        }

        sql.append(" ORDER BY e.date ASC ");

        PreparedStatement ps = connection.prepareStatement(sql.toString());

        // Remplissage dynamique
        for (int i = 0; i < params.size(); i++) {
            ps.setObject(i + 1, params.get(i));
        }

        ResultSet rs = ps.executeQuery();
        List<Event> events = new ArrayList<>();

        while (rs.next()) {

            // Reconstruction de l'event
            Event event;
            String type = rs.getString("event_type");

            switch (type.toUpperCase()) {
                case "CONCERT" -> event = new Concert();
                case "SPECTACLE" -> event = new Spectacle();
                case "CONFERENCE" -> event = new Conference();
                default -> throw new ValidationException("Unknown event : " + type);
            }

            event.setId(rs.getLong("event_id"));
            event.setName(rs.getString("event_name"));
            event.setLocation(rs.getString("event_location"));
            event.setType(type);
            event.setDate(rs.getTimestamp("event_date").toLocalDateTime());

            // Organizer
            EventPlanner planner = new EventPlanner();
            planner.setId(rs.getLong("user_id"));
            planner.setName(rs.getString("user_name"));
            planner.setEmail(rs.getString("user_email"));

            event.setUser(planner);
            event.getCategories().addAll(findByEventId(event));

            events.add(event);
        }

        return events;
    }

    	
    	
    	
    	
  	 
}
