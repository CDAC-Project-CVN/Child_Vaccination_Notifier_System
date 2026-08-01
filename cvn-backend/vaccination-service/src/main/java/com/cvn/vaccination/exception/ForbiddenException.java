package com.cvn.vaccination.exception;

public class ForbiddenException extends RuntimeException {
	public ForbiddenException(String message) {
        super(message);
    }
}
