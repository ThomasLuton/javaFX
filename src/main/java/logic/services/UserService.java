package logic.services;

import com.example.backlogtp.utils.DAO;
import com.example.backlogtp.utils.DAOAccess;
import com.example.backlogtp.utils.Repository;
import com.example.backlogtp.utils.exceptions.ValidationException;

import logic.dtos.UserInfo;
import logic.entities.Client;
import logic.entities.EventPlanner;
import logic.entities.User;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
	
	public List<UserInfo> connect(String email, String password) throws Exception {
		
		if (email == null || email.isBlank()) {
			throw new Exception ("Email cannot be empty");
		}

	   
	    List<Client> clients = daoAccess.list(Client.class);
	    List<EventPlanner> planners = daoAccess.list(EventPlanner.class);
	    
	    List<UserInfo> results = new ArrayList<>();

	    // Chercher dans clients 
	    
	    for (Client c : clients) {
	        if (c.getEmail().equalsIgnoreCase(email)) {

	            if (!c.getPassword().equals(password)) {
	                throw new Exception("Invalid credentials");
	            }

	            results.add(new UserInfo(
	                    c.getName(),
	                    c.getEmail(),
	                    "CLIENT"
	            ));
	        }
	    }
	    
	    // Chercher dans event planners
	    for (EventPlanner ep : planners) {
	        if (ep.getEmail().equalsIgnoreCase(email)) {

	            if (!ep.getPassword().equals(password)) {
	                throw new Exception("Invalid credentials");
	            }

	            results.add(new UserInfo(
	                    ep.getName(),
	                    ep.getEmail(),
	                    "EVENT_PLANNER"
	            ));
	        }
	    }
	    
	    // Aucun user trouvé
	    
	    if (results.isEmpty()) {
	        throw new Exception("Invalid credentials");
	    }

	    return results;
	}


		
		
};
 