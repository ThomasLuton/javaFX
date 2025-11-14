package com.example.backlogtp.utils;

import java.sql.SQLException;

public class Repository {

    private static final String url = "jdbc:postgresql://localhost:5432/php_my_admin";
    private static final String user = "postgres";
    private static final String password = "admin";
    private static final DAOAccess daoACCESS;

    static {
        try {
            System.out.println("Create conncetion with postgres");
            daoACCESS = new DAOAccess.Builder().url(url).user(user).password(password).build();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static DAOAccess getDaoACCESS() {
        return daoACCESS;
    }
}
