package com.ntsphere.ecn.basicweb.controller;

import java.nio.file.Paths;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import com.ntsphere.common.exception.RestBaseException;
import com.ntsphere.common.util.RestResponse;
import com.ntsphere.common.util.WebUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BaseController {
	@Autowired private Environment environment;
	
	
	
	
	
	public String combinePath(String first, String... more) {
		return Paths.get(first, more).toString();
	}
	
	
	public String getActiveProfile() {
		if (environment.getActiveProfiles().length == 0)
			return null;
		
		return environment.getActiveProfiles()[0];
	}
	
	
	
	
	
	/****************************************************************************************************
	 * Exception handlers
	 ****************************************************************************************************/
	@ExceptionHandler(value=RestBaseException.class)
	private ResponseEntity<?> RestBaseExceptionHandler(RestBaseException e) {
		
		log.debug(e.getMessage());
		return RestResponse.build(HttpStatus.FORBIDDEN, "Invalid API version.")
				.responseEntity();
	}
	
	
	
	
	
	/****************************************************************************************************
	 * Request parameter
	 ****************************************************************************************************/
	public String getParameter(String key) throws RestBaseException {
		HttpServletRequest request = WebUtil.getRequest();
		String val = request.getParameter(key);
		
		if (val == null) {
			throw new RestBaseException("Argument '%s' is not exists.", key);
		}
		
		return val;
	}
	
	
	public String getParameter(String key, String defaultVal) {
		HttpServletRequest request = WebUtil.getRequest();
		String val = request.getParameter(key);
		
		if (val == null) {
			return defaultVal;
		}
		
		return val;
	}
	
	
	public Integer getIntParameter(String key) throws RestBaseException {
		try {
			HttpServletRequest request = WebUtil.getRequest();
			String val = request.getParameter(key);
			
			if (val == null) {
				throw new RestBaseException("Argument '%s' is not exists.", key);
			}
			
			return Integer.parseInt(val);
		}
		catch (RestBaseException e) {
			throw e;
		}
		catch (Exception e) {
			throw new RestBaseException("Argument '%s' is not a number.", key);
		}
	}
	
	
	public Integer getIntParameter(String key, Integer defaultVal) {
		try {
			HttpServletRequest request = WebUtil.getRequest();
			String val = request.getParameter(key);
			
			if (val == null) {
				return defaultVal;
			}
			
			return Integer.parseInt(val);
		}
		catch (Exception e) {
			return defaultVal;
		}
	}
	
	
	public Double getDoubleParameter(String key) throws RestBaseException {
		try {
			HttpServletRequest request = WebUtil.getRequest();
			String val = request.getParameter(key);
			
			if (val == null) {
				throw new RestBaseException("Argument '%s' is not exists.", key);
			}
			
			return Double.parseDouble(val);
		}
		catch (RestBaseException e) {
			throw e;
		}
		catch (Exception e) {
			throw new RestBaseException("Argument '%s' is not a double.", key);
		}
	}
	
	
	public Double getDoubleParameter(String key, Double defaultVal) {
		try {
			HttpServletRequest request = WebUtil.getRequest();
			String val = request.getParameter(key);
			
			if (val == null) {
				return defaultVal;
			}
			
			return Double.parseDouble(val);
		}
		catch (Exception e) {
			return defaultVal;
		}
	}
	
	
	
	
	
	/****************************************************************************************************
	 * Numeric string parser
	 ****************************************************************************************************/
	public Integer parseInt(Object value) throws NumberFormatException {
		return Integer.parseInt(value.toString());
	}
	public Integer parseInt(Object value, Integer defaultValue) {
		try {
			return Integer.parseInt(value.toString());
		} catch (NumberFormatException e) {
			return defaultValue;
		}
	}
	
	
	public Double parseDouble(Object value) throws NumberFormatException {
		return Double.parseDouble(value.toString());
	}
	public Double parseDouble(Object value, Double defaultValue) {
		try {
			return Double.parseDouble(value.toString());
		} catch (NumberFormatException e) {
			return defaultValue;
		}
	}
}
