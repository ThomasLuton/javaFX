package com.example.backlogtp.repositories;

import logic.entities.User;

import java.sql.*;
import java.util.Optional;

public class UserRepository {

    private final Connection connection = DataBaseConnection.getConnection();
    public void createUser(User user) throws SQLException {
        String query = "INSERT INTO users (name, email, password, status) VALUES( ?, ?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, user.getName());
        preparedStatement.setString(2, user.getEmail());
        preparedStatement.setString(3, user.getPassword());
        preparedStatement.setString(4, user.getStatus());
        preparedStatement.execute();
    }

    public Optional<User> findByEmail(String email) throws SQLException {
        String query = "SELECT FROM users WHERE email = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, email);
        ResultSet resultSet = preparedStatement.executeQuery();
        return null;
    }

}
