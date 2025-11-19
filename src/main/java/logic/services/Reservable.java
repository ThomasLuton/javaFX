package logic.services;

import logic.entities.Reservation;

import java.sql.SQLException;

public interface Reservable {
    public void book() throws SQLException;
    public boolean isBookable() throws SQLException;
    public void cancel() throws SQLException;
}
