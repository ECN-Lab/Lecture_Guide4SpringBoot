package com.ntsphere.common.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.Assert;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.util.UriUtils;

public class WebUtil {
	
	
	public static HttpServletRequest getRequest() {
		RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
		Assert.state(requestAttributes != null, "Could not find current request via RequestContextHolder");
		Assert.isInstanceOf(ServletRequestAttributes.class, requestAttributes);

		HttpServletRequest servletRequest = ((ServletRequestAttributes) requestAttributes).getRequest();
		Assert.state(servletRequest != null, "Could not find current HttpServletRequest");
		
		return servletRequest;
	}
	
	
	public static HttpServletResponse getResponse() {
		RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
		Assert.state(requestAttributes != null, "Could not find current request via RequestContextHolder");
		Assert.isInstanceOf(ServletRequestAttributes.class, requestAttributes);

		HttpServletResponse servletResponse = ((ServletRequestAttributes) requestAttributes).getResponse();
		Assert.state(servletResponse != null, "Could not find current HttpServletResponse");
		
		return servletResponse;
	}
	
	
	
	
	
	/****************************************************************************************************
	 * URI utility
	 ****************************************************************************************************/
	public static String uriEncode(String src) {
		return UriUtils.encode(src, "UTF-8");
	}
	public static String uriDecode(String src) {
		return UriUtils.decode(src, "UTF-8");
	}
	
	
	public static String uriEncode(String src, String encoding) {
		return UriUtils.encode(src, encoding);
	}
	public static String uriDecode(String src, String encoding) {
		return UriUtils.decode(src, encoding);
	}
}
