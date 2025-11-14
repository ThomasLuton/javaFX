package com.example.backlogtp.utils.mappers;

import com.example.backlogtp.utils.DAOMapper;
import logic.entities.Client;

public class MapperFactory {
    public DAOMapper initMapper(Class table){
        if(table.equals(Client.class)){
            return new ClientMapper();
        }else{
            throw new RuntimeException("No mapper available");
        }
    }
}
