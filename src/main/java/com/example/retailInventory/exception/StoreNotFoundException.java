package com.example.retailInventory.exception;

/**
 * Custom exception class to denote that the requested store was not found.
 * @author PAnusha
 *
 */
public class StoreNotFoundException extends RuntimeException{

	public StoreNotFoundException(String message) {
		super(message);
	}
}
