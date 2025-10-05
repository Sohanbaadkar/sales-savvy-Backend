package com.example.demo.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entities.User;
import com.example.demo.repositories.UserRepo;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/profile")
@CrossOrigin(origins = "http://localhost:5173",allowCredentials = "true")
public class ProfileController {

	@Autowired
	private UserRepo userRepo;


	@GetMapping("/info")
	public ResponseEntity<Map<String,Object>> getProfileInfo(HttpServletRequest request) {
		User authUser = (User) request.getAttribute("authenticatedUser");

		if(authUser==null ) {
			return ResponseEntity.status(401).body(Map.of("error","Unauthorized access"));
		}

		User user = userRepo.findByUsername(authUser.getUsername()).orElseThrow(() -> new IllegalArgumentException("usernot found with username:" +authUser.getUsername()));

		Map<String,Object> userInfo = new HashMap<>();

		userInfo.put("user_name", user.getUsername());
		userInfo.put("email", user.getEmail());
		userInfo.put("role", user.getRole().toString());
		userInfo.put("created_at", user.getCreatedAt());

		return ResponseEntity.ok(userInfo);

	}
}