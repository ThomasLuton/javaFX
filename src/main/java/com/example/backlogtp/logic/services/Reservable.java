package com.example.backlogtp.logic.services;

import java.sql.SQLException;

public interface Reservable {
    public void book() throws SQLException;
    public boolean isBookable() throws SQLException;
    public void cancel() throws SQLException;
}
