package com.example.retailInventory.exception;

/**
 * Custom exception class to denote that product price value has to be greater than 0.
 * @author PAnusha
 *
 */
public class NegativeProductPriceException extends RuntimeException {

	public NegativeProductPriceException(String message) {
		super(message);
	}
}
