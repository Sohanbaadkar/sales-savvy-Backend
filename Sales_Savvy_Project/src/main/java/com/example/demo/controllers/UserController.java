package com.example.demo.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entities.User;
import com.example.demo.services.UserService;

@RestController
@CrossOrigin
@RequestMapping("/api/users")
public class UserController {
	UserService userservice;

	@Autowired
	public UserController(UserService userservice) {
		this.userservice = userservice;
	}
	
	@PostMapping("/register")
	// Response entity wrapper class for http response && RequestBody converts json file to java obj using jacskson
	public ResponseEntity<?> registerUser(@RequestBody User user) { 
		try {
		User registeredUser = userservice.registerUser(user);
		return ResponseEntity.ok(Map.of("message","User Registered Successfully", "user",registeredUser));
		}
		catch(Exception e) {
			return ResponseEntity.badRequest().body(Map.of("error",e.getMessage()));
		}
	
	}
}
