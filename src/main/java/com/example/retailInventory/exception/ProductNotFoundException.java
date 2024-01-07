package com.example.retailInventory.exception;

/**
 * Custom exception class to denote that the requested product was not found.
 * @author PAnusha
 *
 */
public class ProductNotFoundException extends RuntimeException{

	public ProductNotFoundException(String message) {
		super(message);
	}
}
