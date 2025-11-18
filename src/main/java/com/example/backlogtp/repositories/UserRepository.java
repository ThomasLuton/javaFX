package com.example.backlogtp.repositories;

import logic.entities.Client;
import logic.entities.EventPlanner;
import logic.entities.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
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

    public User findByEmail(String email) throws SQLException {
        String query = "SELECT * FROM users WHERE email = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, email);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<User> users = new ArrayList<>();
        while (resultSet.next()){
            String status = resultSet.getString("status");
            User user = status.equals("client") ? new Client(): new EventPlanner();
            user.setName(resultSet.getString("name"));
            user.setEmail(resultSet.getString("email"));
            user.setPassword(resultSet.getString("password"));
            user.setId(resultSet.getLong("id"));
            users.add(user);
        }
        return users.size() == 0 ? null: users.get(0);
    }

    public User findById(Long id) throws SQLException {
        String query = "SELECT * FROM users WHERE id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setLong(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<User> users = new ArrayList<>();
        while (resultSet.next()){
            String status = resultSet.getString("status");
            User user = status.equals("client") ? new Client(): new EventPlanner();
            user.setName(resultSet.getString("name"));
            user.setEmail(resultSet.getString("email"));
            user.setPassword(resultSet.getString("password"));
            user.setId(resultSet.getLong("id"));
            users.add(user);
        }
        return users.size() == 0 ? null: users.get(0);
    }

}
