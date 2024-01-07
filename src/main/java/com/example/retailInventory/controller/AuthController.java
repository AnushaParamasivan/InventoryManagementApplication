package com.example.retailInventory.controller;

import java.util.Arrays;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.retailInventory.service.AuthService;
import com.example.retailInventory.user.User;
import com.example.retailInventory.user.UserRole;

@RestController
public class AuthController {
	
	@Autowired
	AuthService authService;
	
	
	/**
	 * API to login using username and password
	 * @param username
	 * @param password
	 * @return
	 */
	@PostMapping("/login")
	public String AuthenticateAndGetToken(@RequestParam String username, @RequestParam String password) throws UsernameNotFoundException{
	    String token = authService.authenticate(username,password);
	    return token;
	}
}
