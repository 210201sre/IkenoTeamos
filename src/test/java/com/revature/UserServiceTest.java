package com.revature;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.revature.models.Item;
import com.revature.models.ItemType;
import com.revature.models.Ledger;
import com.revature.models.Role;
import com.revature.models.User;
import com.revature.repositories.ItemDAO;
import com.revature.repositories.LedgerDAO;
import com.revature.repositories.UserDAO;
import com.revature.services.LedgerService;
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
		userDAO.save(u);
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
		List<User> userList = userDAO.findAll();
		assertEquals(3, userList.size());
		
		verify(userDAO, times(1)).findAll();
	}
	@Test
	void findSingleUserTest() {
		User u = new User(1, "name", Role.Customer);
		User u2 = new User(2, "name2", Role.Customer);
		userDAO.save(u);
		userDAO.save(u2);
		
		userDAO.findById(2);
		verify(userDAO, times(1)).findById(2);
	}
	@Test
	void deleteUserTest() {		
		User u = new User(1, "name", Role.Customer);
		User u2 = new User(2, "name2", Role.Customer);	
		userDAO.save(u);
		userDAO.save(u2);
		
		userDAO.delete(u2);
		verify(userDAO, times(1)).delete(u2);
	}
}