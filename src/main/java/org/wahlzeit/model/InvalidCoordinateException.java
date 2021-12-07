package org.wahlzeit.model;

public class InvalidCoordinateException extends Exception {
	public InvalidCoordinateException(String message) {
		super(message);
	}

	public InvalidCoordinateException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidCoordinateException(ArithmeticException ex) {
		super("Error due to arithmetic exception.", ex);
	} 
}