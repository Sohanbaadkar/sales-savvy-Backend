package com.example.demo.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.JWTToken;

import jakarta.transaction.Transactional;

@Repository
public interface JWTTokenRepo extends JpaRepository<JWTToken, Integer>{
	
	// to find token by userId
	@Query("SELECT t FROM JWTToken t WHERE t.user.user_id = :userId")
	JWTToken findByUserId(@Param("userId") int userId);
	
	Optional<JWTToken> findByToken(String token);
	
	// Custom query to delete tokens by user ID
	@Modifying
	@Transactional
	@Query("DELETE FROM JWTToken t WHERE t.user.user_id = :userId")
	void deleteByUserId(@Param("userId") int userId);

}

