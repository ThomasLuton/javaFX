package com.example.backlogtp.logic.dtos;

import com.example.backlogtp.utils.UserStatus;

public record UserInfo (

		String name,
		String email,
		UserStatus type, // Client ou Event_Planner
		Long id
		) {
	
}