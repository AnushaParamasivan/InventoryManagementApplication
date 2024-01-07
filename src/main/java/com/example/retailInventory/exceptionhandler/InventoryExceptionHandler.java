package com.example.retailInventory.exceptionhandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.retailInventory.exception.NegativeProductPriceException;
import com.example.retailInventory.exception.ProductNotFoundException;
import com.example.retailInventory.exception.StoreNotFoundException;

/**
 * Global exception handling class to handle custom exceptions, and send appropriate response entity.
 * @author PAnusha
 *
 */
@RestControllerAdvice
public class InventoryExceptionHandler {

	@ExceptionHandler({ProductNotFoundException.class,NegativeProductPriceException.class,StoreNotFoundException.class})
	public ResponseEntity<String> handleProductNotFoundException(Exception p){
		
		ResponseEntity<String> response = new ResponseEntity<String>(p.getMessage(), HttpStatus.BAD_REQUEST);
		return response;
	}

	@ExceptionHandler({UsernameNotFoundException.class,BadCredentialsException.class})
	public ResponseEntity<String> handleUsernameNotFoundException(Exception p){
		
		ResponseEntity<String> response = new ResponseEntity<String>(p.getMessage(), HttpStatus.FORBIDDEN);
		return response;
	}

	
}
