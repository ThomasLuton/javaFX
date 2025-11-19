package com.example.backlogtp.logic.dtos;

public record UserInfo (
		
		String name,
		String email,
		String type, // Client ou Event_Planner
		Long id
		) {
	
}