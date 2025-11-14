package com.example.backlogtp.utils;

import com.example.backlogtp.utils.mappers.MapperFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DAOAccess {
    private final Connection connection;
    private final Statement statement;

    private DAOAccess(String url, String user, String password) throws SQLException {
        this.connection = DriverManager.getConnection(url, user, password);
        this.statement = connection.createStatement();
    }

    public void close() throws SQLException {
        connection.close();
    }

    public List list(Class table) throws SQLException {
        String tableName = table.toString().split(" ")[1].substring(table.getPackageName().length() +1);
        ResultSet resultSet = statement.executeQuery("SELECT * FROM " + tableName);
        List list = new ArrayList<>();
        DAOMapper mapper = new MapperFactory().initMapper(table);
        while (resultSet.next()){
            list.add(mapper.toEntity(resultSet));
        }
        return list;
    }

    public void add(DAO dao) throws SQLException {
        DAOMapper mapper = new MapperFactory().initMapper(dao.getClass());
        statement.executeUpdate(mapper.toCreateRequest(dao));
    }

    public void delete(DAO dao) throws SQLException {
        DAOMapper mapper = new MapperFactory().initMapper(dao.getClass());
        statement.executeUpdate(mapper.toDeleteRequest(dao));
    }
    public void update(DAO dao) throws SQLException {
        DAOMapper mapper = new MapperFactory().initMapper(dao.getClass());
        statement.executeUpdate(mapper.toUpdateRequest(dao));
    }

    static class Builder{
        private String url;
        private String user;
        private String password;

        Builder url(String url){
            this.url = url;
            return this;
        }
        Builder user(String user){
            this.user = user;
            return this;
        }
        Builder password(String password){
            this.password = password;
            return this;
        }
        DAOAccess build() throws SQLException {
            return new DAOAccess(url, user, password);
        }
    }
}
