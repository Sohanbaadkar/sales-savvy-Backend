package com.example.demo.services;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.stereotype.Service;

import com.example.demo.entities.JWTToken;
import com.example.demo.entities.User;
import com.example.demo.repositories.JWTTokenRepo;
import com.example.demo.repositories.UserRepo;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Service
public class AuthService {
	private final Key SIGNING_KEY ;
	
	private final UserRepo userrepo;
	private final JWTTokenRepo jwtTokenRepo;
	private final BCryptPasswordEncoder passwordEncoder;
	
	//injecting jwt.secret from properties file
	@Autowired
	public AuthService( UserRepo userrepo, JWTTokenRepo jwtTokenRepo,@Value("${jwt.secret}") String jwtSecret) {
		
		this.SIGNING_KEY = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
		this.userrepo = userrepo;
		this.jwtTokenRepo = jwtTokenRepo;
		this.passwordEncoder = new BCryptPasswordEncoder();
		
		// ensuring key length is >64 bytes
		if(jwtSecret.getBytes(StandardCharsets.UTF_8).length < 64) {
			throw new IllegalArgumentException("JWT_SECRET in application.properties must be atleast 64 bytes long for HS512.");
		}
	}
	
	public User Authenticate(String username, String password) {
		
		 User user=userrepo.findByUsername(username).orElseThrow(() ->new RuntimeException("Invalid User name"));
		 
		 if(!passwordEncoder.matches(password, user.getPassword())) {
			 throw new RuntimeException("Invalid Password");
		 }
		 return user;
		 
	}
	
	public String generateToken(User user) {
		String token;
		LocalDateTime now = LocalDateTime.now();
		JWTToken existingtoken = jwtTokenRepo.findByUserId(user.getUser_id());
		
		if(existingtoken != null && now.isBefore(existingtoken.getExpires_at())) {
			token = existingtoken.getToken();
			
		}
		else {
			token = generateNewToken(user);
			if(existingtoken != null) {
				jwtTokenRepo.delete(existingtoken);
			}
			else
				saveToken(user,token);
		}
		return token;
	}
	
	private String generateNewToken(User user) {
		return Jwts.builder()
				.setSubject(user.getUsername())
				.claim("role",user.getRole().name())
				.setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + 3600000))
				.signWith(SIGNING_KEY, SignatureAlgorithm.HS512)
				.compact();
		
	}
	
	public void saveToken(User user,String token) {
		JWTToken jwtToken = new JWTToken(user,token,LocalDateTime.now().plusHours(1));
		jwtTokenRepo.save(jwtToken);
	}
	
	public boolean validateToken(String token) {
		try {
			System.err.println("VALIDATING TOKEN..");
			
			// parse and validate the token
			Jwts.parserBuilder()
			.setSigningKey(SIGNING_KEY)
			.build()
			.parseClaimsJws(token);
			
			// check if token exist in database and is not expired
			Optional<JWTToken> jwtToken = jwtTokenRepo.findByToken(token);
			if(jwtToken.isPresent()) {
				System.err.println("Token Expiry: "+jwtToken.get().getExpires_at());
				System.err.println("current Time: "+ LocalDateTime.now());
				return jwtToken.get().getExpires_at().isAfter(LocalDateTime.now());
			}
			return false;
		}
		catch(Exception e) {
			System.err.println("Token validation failed: "+ e.getMessage());
			return false;

		}
	}
	
	public String extractUsername(String token) {
		return Jwts.parserBuilder()
				.setSigningKey(SIGNING_KEY)
				.build()
				.parseClaimsJws(token)
				.getBody()
				.getSubject();
	}
	
	public void logout(String token) {
	    if (token == null) {
	        return; // nothing to do
	    }
	    Optional<JWTToken> jwtToken = jwtTokenRepo.findByToken(token);
	    jwtToken.ifPresent(jwtTokenRepo::delete);
	}

}
