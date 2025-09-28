package com.example.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.entities.User;
import com.example.demo.repositories.UserRepo;

@Service
public class UserService {
	private  UserRepo userrepo ;
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	public UserService(UserRepo userrepo) {
		this.userrepo = userrepo;
		this.passwordEncoder = new BCryptPasswordEncoder();
	}
	
	public User registerUser(User user) {
		// checking weather username exists or not
		if(userrepo.findByUsername(user.getUsername()).isPresent())
			throw new RuntimeException("UserName is already taken!");
		
		// checking weather email exits in db or no
		if(userrepo.findByEmail(user.getEmail()).isPresent())
			throw new RuntimeException("Email already exist Login");
		
		// if everything fine will encode the password
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		
		return userrepo.save(user);
	}
	
}
