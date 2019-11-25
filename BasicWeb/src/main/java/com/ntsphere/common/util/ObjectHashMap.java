package com.ntsphere.common.util;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.support.JdbcUtils;

@SuppressWarnings("serial")
public class ObjectHashMap extends HashMap<String, Object> {
	
	public static ObjectHashMap newInstance() {
		return new ObjectHashMap();
	}
	
	
	public static ObjectHashMap from(HashMap<String, Object> from) {
		ObjectHashMap map = new ObjectHashMap();
		
		if (from != null)
			map.putAll(from);
		return map;
	}
	
	
	public static ObjectHashMap from(HashMap<String, Object> from, boolean convertKeysToCamelCase) {
		ObjectHashMap map = new ObjectHashMap();
		
		if (from != null)
			map.putAll(from);
		
		if (convertKeysToCamelCase == true)
			map.convertKeysToCamelCase();
		
		return map;
	}
	
	
	public Object get(String key, Object def) {
		Object ret = super.getOrDefault(key, def);
		return ret;
	}
	
	
	public ObjectHashMap add(String key, Object val) {
		super.put(key, val);
		return this;
	}
	
	
	public ResponseEntity<HashMap<String, Object>> getResponseEntity(HttpStatus status) {
		return new ResponseEntity<HashMap<String, Object>>(this, status);
	}
	
	
	public void rename(String key, String newKey) {
		Object val = get(key);
		if (val != null) {
			add(newKey, val);
			remove(key);
		}
	}
	
	
	public ObjectHashMap convertKeysToCamelCase() {
		
		HashMap<String, Object> newMap = new HashMap<String, Object>();
		for (Map.Entry<String, Object> iter : entrySet())
		{
			String key = iter.getKey();
			String newKey = JdbcUtils.convertUnderscoreNameToPropertyName(key);

			newMap.put(newKey, iter.getValue());
		}
		
		
		this.clear();
		for (Map.Entry<String, Object> iter : newMap.entrySet())
			put(iter.getKey(), iter.getValue());
		
		
		return this;
	}
}
