package com.example.backlogtp.logic.tests;

import java.util.List;

import com.example.backlogtp.logic.entities.Event;
import com.example.backlogtp.logic.services.EventService;

public class TestEventService {
	
	public static void main(String[] args) {
		try {
			
			//EventRepository repo = new EventRepository();
			
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
		
//		String email = "kenza@"; 
//
//        List<Event> events = repo.findEventsByOrganizerEmail(email);
//         if (events.isEmpty()) {
//             System.out.println("Aucun événement trouvé !");
//         } else {
//             for (Event e : events) {
//                 System.out.println(e.getCategories());
//
//                 System.out.println(
//                         "Event #" + e.getId() +
//                         " | " + e.getName() +
//                         " | " + e.getDate().format(DateTimeFormatter.ofPattern("dd/MM/YYYY")) +
//                         " | " + e.getLocation() +
//                         " | organisé par : " + e.getUser().getName() +
//                         " (" + e.getType() + ")"
//                 );
//             }
//         }
//
//        if (events.isEmpty()) {
//            System.out.println("Aucun événement trouvé pour cet organisateur.");
//        } else {
//            System.out.println("Événements pour " + email + " :");
//            for (Event e : events) {
//                System.out.println(
//                        "#" + e.getId() + " | " +
//                        e.getName() + " | " +
//                        e.getDate() + " | " +
//                        e.getLocation() + " | " +
//                        e.getType() + " | catégories : " + e.getCategories()
//                );
//            }
//        }
//
//    } catch (Exception e) {
//        e.printStackTrace();
//    }
//}
			
			 System.out.println("\n=== Filtre : type = CONCERT ===");
	            List<Event> concerts = eventService.searchEventsForClient("CONCERT", null, null);
	            concerts.forEach(e -> System.out.println(
	                    e.getDate() + " | " + e.getType() + " | " +
	                    e.getName() + " @ " + e.getLocation()
	            ));

	            System.out.println("\n=== Filtre : lieu contient 'Paris' ===");
	            List<Event> paris = eventService.searchEventsForClient(null, "Paris", null);
	            paris.forEach(e -> System.out.println(
	                    e.getDate() + " | " + e.getType() + " | " +
	                    e.getName() + " @ " + e.getLocation()
	            ));

	            System.out.println("\n=== Filtre : nom contient 'Jul' ===");
	            List<Event> mozart = eventService.searchEventsForClient(null, null, "Jul");
	            mozart.forEach(e -> System.out.println(
	                    e.getDate() + " | " + e.getType() + " | " +
	                    e.getName() + " @ " + e.getLocation()
	            ));

	            System.out.println("\n=== Filtre combiné : CONCERT à Paris avec 'Jul' ===");
	            List<Event> combo = eventService.searchEventsForClient("CONCERT", "Paris", "Jul");
	            combo.forEach(e -> System.out.println(
	                    e.getDate() + " | " + e.getType() + " | " +
	                    e.getName() + " @ " + e.getLocation()
	            ));

	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	}
			
			

