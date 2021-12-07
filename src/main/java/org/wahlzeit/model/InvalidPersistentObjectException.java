package org.wahlzeit.model;

public class InvalidPersistentObjectException extends Exception {
	public InvalidPersistentObjectException(String message) {
		super(message);
	}

	public InvalidPersistentObjectException(String message, Throwable cause) {
		super(message, cause);
	}
}