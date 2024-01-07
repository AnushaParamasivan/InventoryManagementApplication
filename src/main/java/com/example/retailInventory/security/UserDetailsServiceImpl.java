package com.example.retailInventory.security;

import java.util.Arrays;
import java.util.HashSet;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.retailInventory.repository.UserRepository;
import com.example.retailInventory.user.CustomUserDetails;
import com.example.retailInventory.user.User;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{

	
	@Autowired
	UserRepository userRepo;
	

	/**
	 * Get user from repository using username.
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		User user = userRepo.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
		return new CustomUserDetails(user);
	}

}
