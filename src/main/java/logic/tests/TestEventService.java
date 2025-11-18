package logic.tests;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

import com.example.backlogtp.repositories.EventRepository;
import com.example.backlogtp.repositories.ReservationRepository;
import logic.dtos.UserInfo;
import logic.entities.Event;
import logic.entities.EventCategory;
import logic.services.EventService;

public class TestEventService {
	public static void main(String[] args) {
		try {
		EventService eventService = new EventService();
//
//		 UserInfo organizer = new UserInfo(
//	                "Kenza",
//	                "kenza@example.com",
//	                "eventPlanner"
//	        );
//
//		 try {
//			 eventService.createEvent(organizer, "Concert", LocalDateTime.now().plusDays(2),"Paris", "CONCERT", List.of(new EventCategory()));
//			 System.out.println("Événement créé ");
//
//			 eventService.createEvent(
//	                    organizer,
//	                    "Conférence Java",
//	                    LocalDateTime.now().plusDays(5),
//	                    "Lyon",
//	                    "CONFERENCE",
//					 List.of(new EventCategory()));
//			System.out.println("Événement créé " );
//		 } catch (Exception e) {
//			 System.out.println("Erreur : " + e.getMessage());
//		 }
//
//	}
		
		 List<Event> events = eventService.listUpcomingEventsForClient();

         if (events.isEmpty()) {
             System.out.println("Aucun événement trouvé !");
         } else {
             for (Event e : events) {
                 System.out.println(
                         "Event #" + e.getId() +
                         " | " + e.getName() +
                         " | " + e.getDate() +
                         " | " + e.getLocation() +
                         " | organisé par : " + e.getUser().getName() +
                         " (" + e.getType() + ")"
                 );
             }
         }

     } catch (Exception e) {
         e.printStackTrace();
     }
 }

}
