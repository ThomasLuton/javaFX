package logic.tests;

import com.example.backlogtp.repositories.EventRepository;
import com.example.backlogtp.repositories.ReservationRepository;
import com.example.backlogtp.repositories.UserRepository;
import logic.dtos.UserInfo;
import logic.entities.Event;
import logic.entities.EventCategory;
import logic.entities.User;
import logic.services.ReservationService;

import java.sql.SQLException;

public class ThomasTest {
    public static void main(String[] args) throws SQLException {
        Event event = new EventRepository().findById(1L);
        EventCategory eventCategory = new EventRepository().findByEventId(event).get(0);

        User user = new UserRepository().findById(1L);
        UserInfo info = new UserInfo(user.getName(), user.getEmail(), user.getStatus());
        new ReservationService().createReservation(info, eventCategory);

        System.out.println(new ReservationService().availablePlace(eventCategory));
    }
}
