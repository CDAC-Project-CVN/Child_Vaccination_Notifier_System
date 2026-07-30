package com.cvn.user.exception;

public class InvalidTokenException extends RuntimeException {
	public InvalidTokenException(String message) {
        super(message);
    }
}
