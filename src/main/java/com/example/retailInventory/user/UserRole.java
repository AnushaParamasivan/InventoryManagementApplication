package com.example.retailInventory.user;

import javax.persistence.Embeddable;


@Embeddable
public class UserRole {

	String role;

	public UserRole(String role) {
		super();
		this.role = role;
	}

	public UserRole() {
		
	}
	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
}
