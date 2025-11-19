package com.example.backlogtp.logic.dtos;

import java.time.LocalDateTime;
import java.util.List;

public record CreateEvent (
        UserInfo userInfo,
        String name,
        LocalDateTime dateTime,
        String location,
        String type,
        List<CreateCategory> categories
){
}
