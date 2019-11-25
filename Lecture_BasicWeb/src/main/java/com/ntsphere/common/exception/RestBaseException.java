package com.ntsphere.common.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

public class RestBaseException extends Exception {

	private static final long serialVersionUID = 1L;
	@Getter private HttpStatus httpStatus;
	@Getter private int subCode;

	
	
	
	
	public RestBaseException() {
	}
	
	
	public RestBaseException(String message, Object... args) {
		super(String.format(message, args));
		
		this.httpStatus = HttpStatus.BAD_REQUEST;
	}
	
	
	public RestBaseException(HttpStatus status, String message, Object... args) {
		super(String.format(message, args));
		
		this.httpStatus = status;
	}
	
	
	public RestBaseException(HttpStatus status, int subCode, String message, Object... args) {
		super(String.format(message, args));
		
		this.httpStatus = status;
		this.subCode = subCode;
	}
}
