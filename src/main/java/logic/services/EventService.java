package logic.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.example.backlogtp.utils.exceptions.ValidationException;

import logic.dtos.UserInfo;
import logic.entities.Concert;
import logic.entities.Conference;
import logic.entities.Event;
import logic.entities.EventCategory;
import logic.entities.Spectacle;

public class EventService {
	
	// Pour tester après ça sera en BDD
	private final List<Event> events = new ArrayList<>();
   
    
    // Vérifier si c'est un eventPlanner
    private boolean isOrganizer(UserInfo info) {
       
        return "eventPlanner".equals(info.type());
                
    }
    
    // Créer un évenement 
    
    public Event createEvent ( UserInfo currentUser,String name, LocalDateTime dateTime, String location, String type) {
    	
    	if (!isOrganizer(currentUser)) {
            throw new ValidationException("Only event's planner can create events.");
        }

        // Valider les champs
        if (name == null || name.isBlank()) {
            throw new ValidationException("Name of event is missng");
        }
        if (dateTime == null) {
            throw new ValidationException("LDate of event is missing");
        }
        if (dateTime.isBefore(LocalDateTime.now())) {
            throw new ValidationException("Date must be in the future");
        }
        if (location == null || location.isBlank()) {
            throw new ValidationException("Location is missing");
        }
        if (type == null || type.isBlank()) {
            throw new ValidationException("Type is missing (CONCERT / SPECTACLE / CONFERENCE)");
        }

        // Créer le bon type d'événement selon "type"
        Event event;
        switch (type.toUpperCase()) {
            case "CONCERT" -> event = new Concert();
            case "SPECTACLE" -> event = new Spectacle();
            case "CONFERENCE" -> event = new Conference();
            default -> throw new ValidationException("Unknown event : " + type);
        }
        
       
        event.setName(name);
        event.setDate(dateTime);
        event.setLocation(location);
        
        // Catégories par défaut - à modifier
        List<EventCategory> categories = new ArrayList<>();
        categories.add(new EventCategory("Standard", 30.0,100));
        categories.add(new EventCategory("VIP", 60.0,30));
        
        event.setCategories(categories);
        
        events.add(event);
        
        return event;
        
    	
    }

	public List<Event> listEvents() {
		
		return new ArrayList<>(events); //retourne une copie de la liste
	}

	
    
    

}
;
