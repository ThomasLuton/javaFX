package logic.entities;

import java.time.LocalDateTime;
import java.util.List;

import com.example.backlogtp.utils.AbstractDAO;

public abstract class Event extends AbstractDAO {
	
	    protected String name;
	    protected LocalDateTime date;
	    protected String location;
	    
	    // Liste de catégories 
	    
	    protected List <EventCategory> categories;


		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public LocalDateTime getDate() {
			return date;
		}

		public void setDate(LocalDateTime date) {
			this.date = date;
		}

		public String getLocation() {
			return location;
		}

		public void setLocation(String location) {
			this.location = location;
		}

		public List<EventCategory> getCategories() {
			return categories;
		}

		public void setCategories(List<EventCategory> categories) {
			this.categories = categories;
		}
		
		@Override
	    public String toString() {
	        return "Evenement{" +
	                ", name='" + name + '\'' +
	                ", date=" + date +
	                ", location='" + location + '\'' +
	                ", categories=" + categories +
	                '}';
	    }
	    
	    

}
