package com.example.backlogtp.logic.services;

import com.example.backlogtp.repositories.UserRepository;
import com.example.backlogtp.utils.PasswordEncoder;
import com.example.backlogtp.utils.exceptions.ValidationException;

import com.example.backlogtp.logic.dtos.UserInfo;
import com.example.backlogtp.logic.entities.Client;
import com.example.backlogtp.logic.entities.EventPlanner;
import com.example.backlogtp.logic.entities.User;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;

public class UserService {
	private final UserRepository users = new UserRepository();
	
	public void createUser(String name, String email, String password, boolean isEventPlanner) throws SQLException, NoSuchAlgorithmException, InvalidKeySpecException {
		isValid(name, email, password);
		User user = isEventPlanner ? new EventPlanner(): new Client();
		
		user.setName(name);
		user.setEmail(email);
		user.setPassword(new PasswordEncoder().encode(password));
		
		users.createUser(user);
	}
	
	private void isValid(String name, String email, String password) throws ValidationException{

	    // Vérification du nom 
	    if (name == null || name.isBlank()) {
	        throw new ValidationException("Nom est vide");
	    }

	    // Nom → uniquement lettres (accents permis) + éventuellement espaces simples
	    if (!name.matches("^[A-Za-zÀ-ÖØ-öø-ÿ]+( [A-Za-zÀ-ÖØ-öø-ÿ]+)*$")) {
			throw new ValidationException("Caractères non permis dans le nom");
	    }

	    // Vérification de l'email 
	    if (email == null || email.isBlank()) {
			throw new ValidationException("Email est vide");
	    }

	    // Contenir @ au minimum
	    if (!email.contains("@")) {
			throw new ValidationException("Email n'est pas un email");
	    }

	    // Vérification du mot de passe 
	    if (password == null || password.isBlank()) {
			throw new ValidationException("Mot de passe vide");
	    }

	    // Min 8 caractères + min 1 chiffre + min 1 caractère spécial
	    // Caractère spécial = tout sauf lettres/chiffres
	    if (!password.matches("^(?=.*[0-9])(?=.*[^A-Za-z0-9]).{8,}$")) {
	        throw new ValidationException("Mot de passe invalide");
	    }

	}
	
	public UserInfo connect(String email, String password) throws Exception {
		User user = users.findByEmail(email);
		if(user == null){
			throw new ValidationException("Wrong credentials");
		}
		if(!new PasswordEncoder().matches(password, user.getPassword())){
			throw new ValidationException("Wrong credentials");
		}
	    return new UserInfo(user.getName(), user.getEmail(), user.getStatus(), user.getId());
	}


		
		
};
 