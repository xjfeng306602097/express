package com.express.exception;

@SuppressWarnings("serial")
public class ExpressClosedException extends RuntimeException {
	public ExpressClosedException(String message, Throwable cause) {
		super(message, cause);
	}

	public ExpressClosedException(String message) {
		super(message);
	}
}
