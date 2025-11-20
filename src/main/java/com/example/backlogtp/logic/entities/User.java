package com.example.backlogtp.logic.entities;

import com.example.backlogtp.utils.AbstractDAO;
import com.example.backlogtp.utils.UserStatus;

public abstract class User extends AbstractDAO {
	
	private String name;
	private String email;
	private String password;
	private UserStatus status;

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public UserStatus getStatus() {
		return status;
	}

	public void setStatus(UserStatus status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "User{" +
				"name='" + name + '\'' +
				", email='" + email + '\'' +
				", password='" + password + '\'' +
				", status='" + status + '\'' +
				'}';
	}
}
