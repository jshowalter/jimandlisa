package com.jimandlisa;

public class ValidationException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2368776440601849862L;

	/**
	 * 
	 */
	public ValidationException() {
	};

	/**
	 * 
	 * @param message
	 */
	public ValidationException(String message) {
		super(message);
	}

	/**
	 * 
	 * @param innerException
	 */
	public ValidationException(Exception innerException) {
		super(innerException);
	}

	/**
	 * 
	 * @param message
	 * @param innerException
	 */
	public ValidationException(String message, Exception innerException) {
		super(message, innerException);
	}

	/**
	 * 
	 * @param innerException
	 */
	public ValidationException(Throwable innerException) {
		super(innerException);
	}
}
