package com.ntsphere.common.util;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RestRequest {
	private String serverUrl = "";
	
	private HashMap<String, Object> pathVariable = new HashMap<String, Object>();
	private HashMap<String, Object> requestParameter = new HashMap<String, Object>();
	private HashMap<String, Object> requestBody = new HashMap<String, Object>();
	
	@Getter private RestTemplate restTemplate = new RestTemplate();
	@Getter private HttpHeaders httpHeaders = new HttpHeaders();
	
	
	
	
	
	//////////////////////////////////////////////////////////////////////
	//  Creators
	public RestRequest() {
	}
	
	
	public RestRequest(String url) {
		serverUrl = url;
	}
	
	
	
	
	
	//////////////////////////////////////////////////////////////////////
	//  Add
	public RestRequest addHeader(String name, String value) {
		httpHeaders.add(name, value);
		return this;
	}
	
	
	public RestRequest addPathVariable(String key, Object value) {
		pathVariable.put(key, value);
		return this;
	}
	
	
	public RestRequest addParameter(String key, Object value) {
		requestParameter.put(key, value);
		return this;
	}
	
	
	public RestRequest addBody(String key, Object value) {
		requestBody.put(key, value);
		return this;
	}
	
	
	
	
	
	//////////////////////////////////////////////////////////////////////
	//  Common
	private String bindPathVariables() {
		String url = serverUrl;
		for (String key : pathVariable.keySet()) {
			url = url.replace("{" + key + "}", pathVariable.get(key).toString());
		}
		

		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
		for (String key : requestParameter.keySet()) {
			builder.queryParam(key, requestParameter.get(key));
		}
		
		String ret = builder.build(false).toUriString();
		return ret;
	}
	
 
	private RestResponse exchange(HttpMethod method, @Nullable HttpEntity<?> requestEntity) {
		
		try {
			URI uri = new URI(bindPathVariables());
			
			log.debug("TargetUrl: " + uri.toString());
			log.debug("Header: " + (requestEntity.getHeaders() != null ? requestEntity.getHeaders().toString() : "null"));
			log.debug("Body: " + (requestEntity.getBody() != null ? requestEntity.getBody().toString() : "null"));
	
	
			ResponseEntity<String> res = restTemplate.exchange(uri, method, requestEntity, String.class);
			String resStr = res.toString();
			if (resStr.length() > 1000)
				log.debug("Response: " + resStr.substring(0, 1000));
			else
				log.debug("Response: " + resStr);
			

			return RestResponse.build(res.getStatusCodeValue(), "")
								.setData(res.getBody());
		}
		catch (HttpStatusCodeException e) {
			String res = e.getResponseBodyAsString();
			log.debug("Response: [HttpStatusCodeException] " + res);
			
			return RestResponse.build(e.getStatusCode(), e.getMessage())
								.setData(res);
		}
		catch (Exception e) {
			log.debug("Response: [Exception] " + e.getMessage());
			
			return RestResponse.build(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}
	
	
	
	
	
	//////////////////////////////////////////////////////////////////////
	//  
	public RestResponse get() {
		HttpEntity<LinkedMultiValueMap<String, String>> entity = new HttpEntity<>(httpHeaders);
		RestResponse res = exchange(HttpMethod.GET, entity);
		return res;
	}
	
	
	public RestResponse post() {
		String body = (new GsonHelper(requestBody)).toString();
		HttpEntity<String> entity = new HttpEntity<>(body, httpHeaders);
		RestResponse res = exchange(HttpMethod.POST, entity);
		return res;
	}
	
	
	public RestResponse postFormData() {
		MultiValueMap<String, Object> formBody = new LinkedMultiValueMap<>();
		for (Map.Entry<String, Object> entity : this.requestBody.entrySet()) {
			formBody.add(entity.getKey(), entity.getValue());
		}


		try {
			URI uri = new URI(bindPathVariables());
			ResponseEntity<String> res = restTemplate.postForEntity(uri, formBody, String.class);
	
			return RestResponse.build(HttpStatus.OK, "")
								.setDataFromJson(res.getBody());
		}
		catch (Exception e) {
			return RestResponse.build(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}
	
	
	public RestResponse put() {
		String body = (new GsonHelper(requestBody)).toString();
		HttpEntity<String> entity = new HttpEntity<>(body, httpHeaders);
		RestResponse res = exchange(HttpMethod.PUT, entity);
		return res;
	}
	
	
	public RestResponse patch() {
		String body = (new GsonHelper(requestBody)).toString();
		HttpEntity<String> entity = new HttpEntity<>(body, httpHeaders);
		RestResponse res = exchange(HttpMethod.PATCH, entity);
		return res;
	}
	
	
	public RestResponse delete() {
		String body = (new GsonHelper(requestBody)).toString();
		HttpEntity<String> entity = new HttpEntity<>(body, httpHeaders);
		RestResponse res = exchange(HttpMethod.DELETE, entity);
		return res;
	}
	
	
	
	
	
	//////////////////////////////////////////////////////////////////////
	//  Etc
	public ObjectMapper newObjectMapper() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	    mapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, false);
	    mapper.configure(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL, true);
        mapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
        
        return mapper;
	}
	
	
	public <T> T getResultMapping(String source, Class<T> clazz) throws Exception {
		if (source == null || source.length() == 0)
			return clazz.newInstance();
		
		ObjectMapper mapper = newObjectMapper();
		return mapper.readValue(source, clazz);
	}
}
