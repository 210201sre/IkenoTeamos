package com.revature.services;

import java.util.List;
import java.util.Optional;

import org.jboss.logging.MDC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.models.User;
import com.revature.repositories.UserDAO;

@Service
public class UserService {
	
	@Autowired
	private UserDAO userDAO;
	private static final Logger log = LoggerFactory.getLogger(UserService.class);

	
	public User addUser(User u) {
		MDC.put("event", "event = Registering new user");
		
		log.info("Registering new User");
		
		MDC.put("userId", "userID = " + Integer.toString(u.getUserID()));
		log.info("Successfully registered User");
		return userDAO.save(u);
	}
	
	public List<User> findAll(){
		MDC.put("event", "Finding all User entries in the database");		
		log.info("Found list of all Users");
		return userDAO.findAll();
	}
	
	public Optional<User> findSingleUser(int id) {
		
		MDC.put("event", "Finding single User in database with ID = " + Integer.toString(id));
		log.info("Successfully found User with ID = " + id);
		return userDAO.findById(id);
	}
	
	public void deleteUser(User u) {
		
		MDC.put("event", "Deleting User information for User in database with ID = " + Integer.toString(u.getUserID()));
		log.info("Delete successful!");

		userDAO.delete(u);
	}
	
	public User update(User u) {
		MDC.put("event", "Updating User information for User in database with ID = " + Integer.toString(u.getUserID()));
		log.info("Update successful!");	

		return userDAO.save(u);		
	}

}
