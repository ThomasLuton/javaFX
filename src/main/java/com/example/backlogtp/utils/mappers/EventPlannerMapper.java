package com.example.backlogtp.utils.mappers;

import com.example.backlogtp.utils.DAOMapper;
import logic.entities.Client;
import logic.entities.EventPlanner;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EventPlannerMapper implements DAOMapper<EventPlanner> {
    @Override
    public EventPlanner toEntity(ResultSet resultSet) throws SQLException {
        EventPlanner client = new EventPlanner();
        client.setName(resultSet.getString("name"));
        client.setEmail(resultSet.getString("email"));
        client.setPassword(resultSet.getString("password"));
        return client;
    }

    @Override
    public String toCreateRequest(EventPlanner dao) {
        String req = "INSERT INTO event_planner (name, email, password) values ('%s', '%s', '%s')";
        return String.format(req, dao.getName(), dao.getEmail(), dao.getPassword());
    }

    @Override
    public String toDeleteRequest(EventPlanner dao) {
        return null;
    }

    @Override
    public String toUpdateRequest(EventPlanner dao) {
        return null;
    }
}
