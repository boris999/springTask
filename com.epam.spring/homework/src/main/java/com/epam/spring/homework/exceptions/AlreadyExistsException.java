package com.epam.spring.homework.exceptions;

public class AlreadyExistsException extends Exception {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public AlreadyExistsException() {
		// TODO Auto-generated constructor stub
	}

	public AlreadyExistsException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public AlreadyExistsException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	public AlreadyExistsException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public AlreadyExistsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

}
