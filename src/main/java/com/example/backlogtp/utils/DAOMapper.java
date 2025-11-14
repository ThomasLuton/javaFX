package com.example.backlogtp.utils;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface DAOMapper<E> {
    public E toEntity(ResultSet resultSet) throws SQLException;
    public String toCreateRequest(E dao);
    public String toDeleteRequest(E dao);
    public String toUpdateRequest(E dao);

}
