package com.jimandlisa;

public class ServiceException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2368776440601849862L;

	/**
	 * 
	 */
	public ServiceException() {
	};

	/**
	 * 
	 * @param message
	 */
	public ServiceException(String message) {
		super(message);
	}

	/**
	 * 
	 * @param innerException
	 */
	public ServiceException(Exception innerException) {
		super(innerException);
	}

	/**
	 * 
	 * @param message
	 * @param innerException
	 */
	public ServiceException(String message, Exception innerException) {
		super(message, innerException);
	}

	/**
	 * 
	 * @param innerException
	 */
	public ServiceException(Throwable innerException) {
		super(innerException);
	}
}
