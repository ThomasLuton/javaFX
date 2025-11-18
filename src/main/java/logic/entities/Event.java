package logic.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.example.backlogtp.utils.AbstractDAO;

public abstract class Event extends AbstractDAO {
	
	    private String name;
	    private LocalDateTime date;
	    private String location;

		private User user;
		private String type;
	    
	    // Liste de catégories 
	    
	    private List<EventCategory> categories = new ArrayList<>();


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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "Event{" +
				"name='" + name + '\'' +
				", date=" + date +
				", location='" + location + '\'' +
				", user=" + user +
				", categories=" + categories +
				'}';
	}
}
