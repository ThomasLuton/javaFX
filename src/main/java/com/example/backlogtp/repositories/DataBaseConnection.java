package com.example.backlogtp.repositories;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBaseConnection {
    private static final String url = "jdbc:postgresql://localhost:5432/event_planner";
    private static final String user = "postgres";
    private static final String password = "admin";

    private static final Connection connection;
    private static final Statement statement;

    static {
        try {
            connection = DriverManager.getConnection(url, user, password);
            statement = connection.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Connection getConnection(){
        return connection;
    }
    public static Statement getStatement() {
        return statement;
    }
}
