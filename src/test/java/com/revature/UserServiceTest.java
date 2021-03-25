package com.revature;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import com.revature.models.Role;
import com.revature.models.User;
import com.revature.repositories.UserDAO;
import com.revature.services.UserService;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
	
	@InjectMocks
	UserService userService;
	
	@Mock
	UserDAO userDAO;
	
	User testUser = new User();
			
	@BeforeAll
	public static void init() {
	}

	@Test
	void addUserTest() {
		User u = new User(1, "name", Role.Customer);
		userService.addUser(u);
		verify(userDAO, times(1)).save(u);
	}
	@Test
	void findAllUsersTest() {		
		List<User> list = new ArrayList<User>();
	
		User u = new User();
		User u2 = new User();
		User u3 = new User();		
		list.add(u);
		list.add(u2);
		list.add(u3);
		
		when(userDAO.findAll()).thenReturn(list);	
		List<User> userList = userService.findAll();
		assertEquals(3, userList.size());
		
		verify(userDAO, times(1)).findAll();
	}
	
	@Test
	void findSingleUserTest() {
		Optional<User> u = Optional.of(new User(1, "John Doe", Role.Customer));
		when(userDAO.findById(1)).thenReturn(u);
		
		assertEquals(u, userService.findSingleUser(1));
		
	}
	
	@Test
	void deleteUserTest() {		
		User u = new User(1, "name", Role.Customer);		
		userService.deleteUser(u);
		verify(userDAO, times(1)).delete(u);
	}
	
	@Test
	void updateUserTest() {
		User u = new User(1, "John Smith", Role.Customer);
		userService.update(u);
		verify(userDAO, times(1)).save(u);
	}
}
