package logic.services;

import logic.entities.Client;
import logic.entities.EventPlanner;
import logic.entities.User;

public class UserService {
	
	public void createUser(String name, String email, String password, boolean isEventPlanner) {
		if(!isValid(name, email, password)) {
			throw new RuntimeException("Error");
		}
		User user;
		if (isEventPlanner) {
			user = new EventPlanner();
		} else {
			user = new Client();
		}
		
		user.setName(name);
		user.setEmail(email);
		user.setPassword(password);
		
		System.out.println(user);
	}
	
	private boolean isValid(String name, String email, String password) {

	    // ---- Vérification du nom ----
	    if (name == null || name.isBlank()) {
	        return false;
	    }

	    // Nom → uniquement lettres (accents permis) + éventuellement espaces simples
	    if (!name.matches("^[A-Za-zÀ-ÖØ-öø-ÿ]+( [A-Za-zÀ-ÖØ-öø-ÿ]+)*$")) {
	        return false;
	    }

	    // ---- Vérification de l'email ----
	    if (email == null || email.isBlank()) {
	        return false;
	    }

	    // Contenir @ au minimum
	    if (!email.contains("@")) {
	        return false;
	    }

	    // ---- Vérification du mot de passe ----
	    if (password == null || password.isBlank()) {
	        return false;
	    }

	    // Min 8 caractères + min 1 chiffre + min 1 caractère spécial
	    // Caractère spécial = tout sauf lettres/chiffres
	    if (!password.matches("^(?=.*[0-9])(?=.*[^A-Za-z0-9]).{8,}$")) {
	        return false;
	    }

	    return true;
	}

		
		
}
