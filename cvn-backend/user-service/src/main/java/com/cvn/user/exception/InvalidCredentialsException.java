package com.cvn.user.exception;

public class InvalidCredentialsException extends RuntimeException {
	public InvalidCredentialsException(String message) {
        super(message);
    }
}
