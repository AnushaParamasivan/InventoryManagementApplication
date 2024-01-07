package com.example.retailInventory.service;

import java.util.Arrays;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import com.example.retailInventory.repository.UserRepository;
import com.example.retailInventory.security.JwtService;
import com.example.retailInventory.user.User;
import com.example.retailInventory.user.UserRole;

@Service
public class AuthService {
	
	@Autowired
	JwtService jwtService;
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	UserRepository userRepo;
	
	/**
	 * Load sample users to user repository on startup
	 */
	@PostConstruct
	public void loadUsers() {
		 
		userRepo.save(new User("user1",new BCryptPasswordEncoder().encode("pwd1"),Arrays.asList(new UserRole("ROLE_ADMIN"),new UserRole("ROLE_STAFF"))));
		userRepo.save(new User("user2",new BCryptPasswordEncoder().encode("pwd2"),Arrays.asList(new UserRole("ROLE_STAFF"))));
	}
	
	
	
	/**
	 * API to login using username and password
	 * @param username
	 * @param password
	 * @return
	 */
	public String authenticate(String username, String password){
	    Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
	    if(authentication.isAuthenticated()){
	       return jwtService.generateToken(authentication);
	    } else {
	        throw new UsernameNotFoundException("invalid user request..!!");
	    }
	}
}
