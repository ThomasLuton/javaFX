package logic.services;

import com.example.backlogtp.utils.DAOAccess;
import com.example.backlogtp.utils.Repository;
import com.example.backlogtp.utils.exceptions.ValidationException;
import logic.entities.Client;
import logic.entities.EventPlanner;
import logic.entities.User;

import java.sql.SQLException;

public class UserService {

	private final DAOAccess daoAccess = Repository.getDaoACCESS();
	
	public void createUser(String name, String email, String password, boolean isEventPlanner) throws SQLException {
		isValid(name, email, password);
		User user;
		if (isEventPlanner) {
			user = new EventPlanner();
		} else {
			user = new Client();
		}
		
		user.setName(name);
		user.setEmail(email);
		user.setPassword(password);
		
		daoAccess.add(user);
	}
	
	private void isValid(String name, String email, String password) throws ValidationException{

	    // ---- Vérification du nom ----
	    if (name == null || name.isBlank()) {
	        throw new ValidationException("Nom est vide");
	    }

	    // Nom → uniquement lettres (accents permis) + éventuellement espaces simples
	    if (!name.matches("^[A-Za-zÀ-ÖØ-öø-ÿ]+( [A-Za-zÀ-ÖØ-öø-ÿ]+)*$")) {
			throw new ValidationException("Caractères non permis dans le nom");
	    }

	    // ---- Vérification de l'email ----
	    if (email == null || email.isBlank()) {
			throw new ValidationException("Email est vide");
	    }

	    // Contenir @ au minimum
	    if (!email.contains("@")) {
			throw new ValidationException("Email n'est pas un email");
	    }

	    // ---- Vérification du mot de passe ----
	    if (password == null || password.isBlank()) {
			throw new ValidationException("Mot de passe vide");
	    }

	    // Min 8 caractères + min 1 chiffre + min 1 caractère spécial
	    // Caractère spécial = tout sauf lettres/chiffres
	    if (!password.matches("^(?=.*[0-9])(?=.*[^A-Za-z0-9]).{8,}$")) {
	        throw new ValidationException("Mot de passe invalide");
	    }

	}

		
		
}
