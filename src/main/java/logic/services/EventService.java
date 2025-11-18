package logic.services;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.example.backlogtp.repositories.EventRepository;
import com.example.backlogtp.repositories.UserRepository;
import com.example.backlogtp.utils.exceptions.ValidationException;

import logic.dtos.CreateCategory;
import logic.dtos.CreateEvent;
import logic.dtos.UserInfo;
import logic.entities.User;
import logic.entities.Concert;
import logic.entities.Conference;
import logic.entities.Event;
import logic.entities.EventCategory;
import logic.entities.Spectacle;

public class EventService {

    private final EventRepository events = new EventRepository();

    public void createEvent (CreateEvent input) throws SQLException {
    	
    	if (!input.userInfo().type().equals("eventPlanner")) {
            throw new ValidationException("Only event's planner can create events.");
        }

        // Valider les champs
        if (input.name() == null || input.name().isBlank()) {
            throw new ValidationException("Name of event is missng");
        }
        if (input.dateTime() == null) {
            throw new ValidationException("LDate of event is missing");
        }
        if (input.dateTime().isBefore(LocalDateTime.now())) {
            throw new ValidationException("Date must be in the future");
        }
        if (input.location() == null || input.location().isBlank()) {
            throw new ValidationException("Location is missing");
        }
        if (input.type() == null || input.type().isBlank()) {
            throw new ValidationException("Type is missing (CONCERT / SPECTACLE / CONFERENCE)");
        }

        // Créer le bon type d'événement selon "type"
        Event event;
        switch (input.type().toUpperCase()) {
            case "CONCERT" -> event = new Concert();
            case "SPECTACLE" -> event = new Spectacle();
            case "CONFERENCE" -> event = new Conference();
            default -> throw new ValidationException("Unknown event : " + input.type());
        }
        
       
        event.setName(input.name());
        event.setDate(input.dateTime());
        event.setLocation(input.location());
        event.setUser(new UserRepository().findByEmail(input.userInfo().email()));

        events.createEvent(event);
        Event freshEvent = events.findLast();

        input.categories().forEach(c -> {
            EventCategory eventCategory = new EventCategory();
            eventCategory.setName(c.name());
            eventCategory.setCapacity(c.capacity());
            eventCategory.setPrice(c.price());
            eventCategory.setEvent(freshEvent);
            try {
                events.createEventCategory(eventCategory);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });

    }
    
    public List<Event> listUpcomingEventsForClient() throws SQLException {
        return events.findUpcomingEventsSortedByDate();
    }
    
    public List<Event> listEventsForOrganizer(UserInfo userInfo) throws Exception {

        if (!"eventPlanner".equalsIgnoreCase(userInfo.type())) {
            throw new ValidationException("Only event planners can see their events");
        }

        return events.findEventsByOrganizerEmail(userInfo.email());
    }

    
    
}
;
