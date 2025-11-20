package com.example.backlogtp.logic.entities;

import com.example.backlogtp.utils.UserStatus;

public class EventPlanner extends User {

    public EventPlanner(){
        setStatus(UserStatus.EVENTPLANNER);
    }

}
