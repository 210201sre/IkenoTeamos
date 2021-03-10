package com.revature.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.models.User;
import com.revature.services.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping
	public ResponseEntity<List<User>> findAll() {
		return ResponseEntity.ok(userService.findAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Optional<User>> findSingleUser(@PathVariable("id") int userID) {
		return ResponseEntity.ok(userService.findSingleUser(userID));
	}

	@PostMapping(produces = { "application/json" }, consumes = { "application/json" })
	public ResponseEntity<User> addUser(@RequestBody User u) {
		return ResponseEntity.ok(userService.addUser(u));
	}

	@PutMapping
	public ResponseEntity<User> updateUser(@RequestBody User u) {
		return ResponseEntity.ok(userService.update(u));
	}

	@DeleteMapping
	public void deleteUser(@RequestBody User u) {
		userService.deleteUser(u);
	}

}
