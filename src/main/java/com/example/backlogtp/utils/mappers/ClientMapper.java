package com.example.backlogtp.utils.mappers;

import com.example.backlogtp.utils.DAOMapper;
import logic.entities.Client;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ClientMapper implements DAOMapper<Client> {
    @Override
    public Client toEntity(ResultSet resultSet) throws SQLException {
        return null;
    }

    @Override
    public String toCreateRequest(Client dao) {
        String req = "INSERT INTO client (name, email, password) values ('%s', '%s', '%s')";
        return String.format(req, dao.getName(), dao.getEmail(), dao.getPassword());
    }

    @Override
    public String toDeleteRequest(Client dao) {
        return null;
    }

    @Override
    public String toUpdateRequest(Client dao) {
        return null;
    }
}
