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

/**
 * Classe responsable de la logique métier liée aux événements
 * 
 * Elle s'occupe principalement de : 
 * 
 * - la création d'un nouvel évenement pour l'organisateur (eventPlanner)
 * - récuperer la liste des évenements futurs pour les clients
 * - récuperer la liste des évenements créer par un organisateur
 * - filtrage de la liste des évenements par catégorie pour les clients 
 * 
 * 
 */

public class EventService {

    private final EventRepository events = new EventRepository();
    
    /**
     * Méthode pour créer évenement.
     * 
     * Cette méthode sert à créer un nouvel évenement pour les organisateurs.
     * 
     * @param input : un DTO 
     * @throws SQLException
     * 		   ValidationException
     */

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
    
    /*
     * Méthode qui renvoie la liste des évenements futurs triés par date pour les clients.
     *  
     * @throws SQLException
     * 		   ValidationException
     */
    public List<Event> listUpcomingEventsForClient() throws SQLException {
        return events.findUpcomingEventsSortedByDate();
    }
    
    /*
     * Méthode qui renvoie la liste des évenements créers par un organisateur.
     * 
     * @throws SQLException
     * 		   ValidationException
     */
    public List<Event> listEventsForOrganizer(UserInfo userInfo) throws Exception {

        if (!"eventPlanner".equalsIgnoreCase(userInfo.type())) {
            throw new ValidationException("Only event planners can see their events");
        }

        return events.findEventsByOrganizerEmail(userInfo.email());
    }
    
    /*
     * Méthode qui renvoie la liste des évenements filtré par type (Concert,Spectacle,Conference) , 
     * par lieu et par nom (Pour les clients).
     * 
     *@params typeFilter : Concert, Spectacle, Conference
     *		  locationFilter : Lieu
     *	      nameFilter : Nom de l'évenement
     * @throws SQLException
     * 		   ValidationException
     */
    public List<Event> searchEventsForClient(String typeFilter, String locationFilter, String nameFilter) throws SQLException {
    	
    	return events.findUpcomingEventsFiltered(typeFilter, locationFilter, nameFilter);
    }

    
    
}
;
