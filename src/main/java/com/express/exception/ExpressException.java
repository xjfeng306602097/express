package com.express.exception;

@SuppressWarnings("serial")
public class ExpressException extends RuntimeException {
	public ExpressException(String message, Throwable cause) {
		super(message, cause);
	}

	public ExpressException(String message) {
		super(message);
	}
}
