package logic.tests;

import java.time.LocalDateTime;
import java.util.List;

import logic.dtos.UserInfo;
import logic.entities.Event;
import logic.services.EventService;

public class TestEventService {
	
	public static void main(String[] args) {
		
		EventService eventService = new EventService(); 
		
		 UserInfo organizer = new UserInfo(
	                "Kenza",
	                "kenza@example.com",
	                "eventPlanner"   
	        );
		 
		 try {
			 Event event1 = eventService.createEvent(organizer, "Concert", LocalDateTime.now().plusDays(2),"Paris", "CONCERT");
			 
			 System.out.println("Événement créé : " + event1);
			 
			 Event event2 = eventService.createEvent(
	                    organizer,
	                    "Conférence Java",
	                    LocalDateTime.now().plusDays(5),
	                    "Lyon",
	                    "CONFERENCE");
			System.out.println("Événement créé : " + event2);
			
			List<Event> events = eventService.listEvents();
			
			System.out.println("\nListe des événements :");

			for (Event ev : events) {
			    System.out.println(" - " + ev.getName() + " | " + ev.getDate());
			}
			
			
		
		 } catch (Exception e) {
			 System.out.println("Erreur : " + e.getMessage());
		 }
		
	}

}
