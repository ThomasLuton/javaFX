package logic.entities;

public class EventCategory {

    private String name;   // "Standard", "VIP"…
    private double price;  // prix d'un ticket
    private int capacity;  // nombre total de places disponibles

	private Event event;
    public EventCategory() {}

    public EventCategory(String name, double price, int capacity) {
        this.name = name;
        this.price = price;
        this.capacity = capacity;
    }


    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	@Override
    public String toString() {
        return "EventCategory{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", capacity=" + capacity +
                '}';
    }
}
