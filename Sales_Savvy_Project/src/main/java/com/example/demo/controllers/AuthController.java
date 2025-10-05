package com.example.demo.controllers;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.LoginRequest;
import com.example.demo.entities.User;
import com.example.demo.services.AuthService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
@RequestMapping("/api/auth")
public class AuthController {
	
	private final AuthService authservice;

	public AuthController(AuthService authservice) {
		super();
		this.authservice = authservice;
	}
	
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginRequest loginrequest, HttpServletResponse response) {
		try {
		User user = authservice.Authenticate(loginrequest.getUsername(), loginrequest.getPassword());
		String token = authservice.generateToken(user);
		
		Cookie cookie = new Cookie("authToken",token);
		cookie.setHttpOnly(true);
		cookie.setSecure(false); // set true if https
		cookie.setMaxAge(3600);
		cookie.setPath("/");   // set path for the local host
		cookie.setDomain("localhost");
		response.addCookie(cookie);
		//optional
		response.addHeader("set-cookie", String.format("authToken=%s; HttpOnly; Path=/; maxAge=3600; sameSite=None", token));
		
		Map<String,Object> responsebody = new HashMap<>();
		responsebody.put("message", "Login successful");
		responsebody.put("role", user.getRole().name());
		responsebody.put("username", user.getUsername());
		return ResponseEntity.ok(responsebody);
		}
		
		catch(Exception e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error",e.getMessage()));
		}
	}
	
	@PostMapping("/logout")
	public ResponseEntity<Map<String, String>> logout(HttpServletRequest request, HttpServletResponse response) {
	    // Clear the authentication token cookie
	    Cookie cookie = new Cookie("authToken", null);
	    cookie.setHttpOnly(true);
	    cookie.setSecure(false); // true in production with HTTPS
	    cookie.setMaxAge(0);
	    cookie.setPath("/");
	    response.addCookie(cookie);

	    // Optional: invalidate the token on server side
	    String token = null;
	    if (request.getCookies() != null) {
	        token = Arrays.stream(request.getCookies())
	                      .filter(c -> "authToken".equals(c.getName()))
	                      .map(Cookie::getValue)
	                      .findFirst()
	                      .orElse(null);
	    }
	    if (token != null) {
	        authservice.logout(token); // overload your service to accept token instead of user
	    }

	    Map<String, String> responseBody = new HashMap<>();
	    responseBody.put("message", "Logout successful");
	    return ResponseEntity.ok(responseBody);
	}

}
