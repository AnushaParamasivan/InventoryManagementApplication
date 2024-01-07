package com.example.retailInventory.user;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Custom implementation of UserDetails, to maintain username, password and roles associated.
 * @author PAnusha
 *
 */
public class CustomUserDetails extends User implements UserDetails{

	
	List<GrantedAuthority>  authorities;
    
    public CustomUserDetails(User user) {
        this.setUsername(user.getUsername());
        this.setPassword(user.getPassword());
        authorities = new ArrayList<>();

        for(UserRole role : user.getRoles()){

        	authorities.add(new SimpleGrantedAuthority(role.getRole().toUpperCase()));
        }
        this.authorities = authorities;
    }
    
    
	@Override
	public List< GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return this.authorities;
	}

	@Override
	public String getPassword() {
		return super.getPassword();
	}

	@Override
	public String getUsername() {
		return super.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
