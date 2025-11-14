package com.example.backlogtp.utils.mappers;

import com.example.backlogtp.utils.DAOMapper;

public class MapperFactory {
    public DAOMapper initMapper(Class table){
        throw new RuntimeException("No mapper available");
    }
}
