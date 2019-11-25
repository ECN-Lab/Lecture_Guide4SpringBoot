package com.ntsphere.common.exception;

public class InvalidArgumentException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public InvalidArgumentException() {
		super("InvalidArgumentException");
	}
	
	
	public InvalidArgumentException(String message) {
		super(message);
	}
}
