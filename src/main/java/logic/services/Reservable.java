package logic.services;

import logic.entities.Reservation;

public interface Reservable {
    public void book(Reservation reservation);
    public boolean isBookable();
    public void cancel();
}
