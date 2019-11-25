package com.ntsphere.common.util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.ntsphere.common.exception.InvalidArgumentException;

import lombok.Getter;

public class RestResponse {
	private int code = 0;
	private int subCode = 0;
	private String message = "";
	private Object data = new HashMap<>();
	@Getter private transient HttpHeaders httpHeaders = new HttpHeaders();
	
	
	
	
	
	public RestResponse() {
		addHeader("Content-Type", "application/json");
	}
	
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	//  Member methods
	public ResponseEntity<String> responseEntity() {
		String json = toJsonString();
		ResponseEntity<String> re = new ResponseEntity<String>(json, httpHeaders, HttpStatus.valueOf(code));

		return re;
	}
	
	
	public void writeToServletResponse(HttpServletResponse response) {
		try {
	    	String jsonStr = toJsonString();
	    	
	    	response.addHeader("Content-Type", "application/json");
	    	response.setStatus(code);
	    	response.getWriter().write(jsonStr);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public HttpHeaders getHeaders() {
		return httpHeaders;
	}
	
	
	public RestResponse addHeader(String name, String value) {
		httpHeaders.add(name, value);
		return this;
	}
	
	
	public RestResponse addHeader(String name, List<String> values) {
		httpHeaders.addAll(name, values);
		return this;
	}
	
	
	public RestResponse addHeader(String name, String... values) throws InvalidArgumentException {
		if (values == null || values.length == 0) {
			throw new InvalidArgumentException("There must be at least one value.");
		}
		
		httpHeaders.addAll(name, Arrays.asList(values));
		return this;
	}
	
	
	public HttpStatus getHttpStatus() {
		return HttpStatus.valueOf(code);
	}
	
	
	public int getCode() { return this.code; }
	public RestResponse setCode(int code) {
		this.code = code;
		return this;
	}
	
	
	public int getSubCode() { return this.subCode; }
	public RestResponse setSubCode(int subCode) {
		this.subCode = subCode;
		return this;
	}
	
	
	public String getMessage() { return this.message; }
	public RestResponse setMessage(String message) {
		this.message = (message != null ? message : "");
		return this;
	}
	
	
	public Object getData() { return this.data; }
	public RestResponse setData(Object data) {
		this.data = data;
		return this;
	}
	public RestResponse setDataFromJson(String jsonString) {
		if (jsonString == null || jsonString.length() == 0) {
			this.data = null;
			return this;
		}
		
		this.data = (new GsonHelper(jsonString)).getElement();
		return this;
	}
	
	
	public String toJsonString() {
		return (new GsonHelper(this)).toString();
	}
	
	
	public GsonHelper toJson() {
		return (new GsonHelper(this));
	}
	
	public GsonHelper dataToJson() {
		return (new GsonHelper(data));
	}
	
	
	
	
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	//  Static methods
	public static RestResponse build(int code, String message) {
		RestResponse instance = new RestResponse();
		instance.setCode(code);
		instance.setMessage(message);
		return instance;
	}
	
	
	public static RestResponse build(HttpStatus code, String message) {
		RestResponse instance = new RestResponse();
		instance.setCode(code.value());
		instance.setMessage(message);
		return instance;
	}
	
	
	public static RestResponse ok() {
		RestResponse instance = new RestResponse();
		instance.setCode(HttpStatus.OK.value());
		instance.setMessage("");
		return instance;
	}
	
	
	public static RestResponse ok(String message) {
		RestResponse instance = new RestResponse();
		instance.setCode(HttpStatus.OK.value());
		instance.setMessage(message);
		return instance;
	}
	
	
	public static RestResponse forbidden(String message) {
		RestResponse instance = new RestResponse();
		instance.setCode(HttpStatus.FORBIDDEN.value());
		instance.setMessage(message);
		return instance;
	}
	
	
	public static RestResponse badRequest(String message) {
		RestResponse instance = new RestResponse();
		instance.setCode(HttpStatus.BAD_REQUEST.value());
		instance.setMessage(message);
		return instance;
	}
	
	
	public static RestResponse unauthorized(String message) {
		RestResponse instance = new RestResponse();
		instance.setCode(HttpStatus.UNAUTHORIZED.value());
		instance.setMessage(message);
		return instance;
	}
	
	
	public static RestResponse notImplemented(String message) {
		RestResponse instance = new RestResponse();
		instance.setCode(HttpStatus.NOT_IMPLEMENTED.value());
		instance.setMessage(message);
		return instance;
	}
	
	
	public static RestResponse internalServerError(String message) {
		RestResponse instance = new RestResponse();
		instance.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		instance.setMessage(message);
		return instance;
	}
	
	
	public static RestResponse internalServerError(Exception e) {
		e.printStackTrace();
		
		RestResponse instance = new RestResponse();
		instance.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		instance.setMessage(e.getMessage());
		return instance;
	}
	
	
	public static RestResponse serviceUnavailable(String message) {
		RestResponse instance = new RestResponse();
		instance.setCode(HttpStatus.SERVICE_UNAVAILABLE.value());
		instance.setMessage(message);
		return instance;
	}
}
