package com.example.retailInventory.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.retailInventory.user.User;

/**
 * Repository for user details for authentication
 * @author PAnusha
 *
 */
public interface UserRepository extends JpaRepository<User, Integer>{

	Optional<User> findByUsername(String username);

}
