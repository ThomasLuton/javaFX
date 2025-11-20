package com.example.backlogtp.logic.entities;

import com.example.backlogtp.utils.UserStatus;

public class Client extends User {

    public Client(){
        setStatus(UserStatus.CLIENT);
    }
}
