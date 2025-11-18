package logic.tests;

import java.time.LocalDateTime;
import java.util.List;

import com.example.backlogtp.repositories.EventRepository;
import logic.dtos.UserInfo;
import logic.entities.Event;
import logic.entities.EventCategory;
import logic.services.EventService;

public class TestEventService {
	
	public static void main(String[] args) {
		try {
			
			EventRepository repo = new EventRepository();
		//EventService eventService = new EventService();
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
		
//		 List<Event> events = eventService.listUpcomingEventsForClient();
//
//        if (events.isEmpty()) {
//           System.out.println("Aucun événement trouvé !");
//         } else {
//             for (Event e : events) {
//                 System.out.println(
//                         "Event #" + e.getId() +
//                         " | " + e.getName() +
//                         " | " + e.getDate() +
//                         " | " + e.getLocation() +
//                         " | organisé par : " + e.getUser().getName() +
//                         " (" + e.getType() + ")"
//                 );
//             }
//         }
//
//     } catch (Exception e) {
//         e.printStackTrace();
//     }
// }
		
		String email = "kenza@"; // mets un email qui existe en BDD

        List<Event> events = repo.findEventsByOrganizerEmail(email);

        if (events.isEmpty()) {
            System.out.println("Aucun événement trouvé pour cet organisateur.");
        } else {
            System.out.println("Événements pour " + email + " :");
            for (Event e : events) {
                System.out.println(
                        "#" + e.getId() + " | " +
                        e.getName() + " | " +
                        e.getDate() + " | " +
                        e.getLocation() + " | " +
                        e.getType() + " | catégories : " + e.getCategories()
                );
            }
        }

    } catch (Exception e) {
        e.printStackTrace();
    }
}

}
