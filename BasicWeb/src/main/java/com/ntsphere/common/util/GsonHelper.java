package com.ntsphere.common.util;

import java.lang.reflect.Type;
import java.util.Collection;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

/**
 * @author reinl
 *
 *
		String jsonText = "{'glossary':{'title':'example glossary','GlossDiv':{'title':'S','GlossList':{'GlossEntry':{'ID':'SGML','SortAs':'SGML','GlossTerm':'Standard Generalized Markup Language','Acronym':'SGML','Abbrev':'ISO 8879:1986','GlossDef':{'para':'A meta-markup language, used to create markup languages such as DocBook.','GlossSeeAlso':['GML','XML']},'GlossSee':'markup'}}}}}";
		
		try {
			GsonHelper gson = new GsonHelper(jsonText);
			
			//  text로 변환하기
			jsonText = gson.toString();
			
			
			//  Json에서 배열 가져오기
			Collection<Object> list = gson.root().on("glossary/GlossDiv/GlossList/GlossEntry/GlossDef/GlossSeeAlso").getArray();
			for (Object o : list) {
			}
			
			
			//  Json에서 값 가져오기
			String val = gson.root().on("glossary/title").getStringValue();
			
			
			//  배열을 Json으로 변환하기
			String[] strArray = {"1", "Two", "삼"};
			String strArrayJson = (new GsonHelper(strArray)).toString();
			
			
			//  객체를 Json으로 변환하기
			String strObjectJson = (new GsonHelper(new AuthUser())).toString();
			strObjectJson = null;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
 */
public class GsonHelper
{
	private JsonElement root, curNode;
	
	
	
	
	
	public static GsonHelper parse(String jsonText) {
		return new GsonHelper(jsonText);
	}
	
	
	public GsonHelper()
	{
	}
	
	
	public GsonHelper(String jsonText)
	{
		root = (new JsonParser()).parse(jsonText);
		curNode = root;
	}
	
	
	public GsonHelper(Object obj)
	{
		if (obj instanceof String)
		{
			root = (new JsonParser()).parse(obj.toString());
			curNode = root;
			return;
		}
		
		String jsonText = (new Gson()).toJson(obj);
		root = (new JsonParser()).parse(jsonText);
		curNode = root;
	}
	
	
	public GsonHelper(JsonElement obj)
	{
		root = obj.deepCopy();
		curNode = root;
	}
	
	
	public GsonHelper(GsonHelper obj)
	{
		root = obj.root.deepCopy();
		curNode = root;
	}
	
	
	public String toString()
	{
		return root.toString();
	}
	
	
	/**
	 * 현재 위치의 JsonElement를 가져옵니다.
	 * @return
	 */
	public JsonElement getElement()
	{
		return curNode;
	}
	
	
	/**
	 * 현재 위치의 JsonObject를 가져옵니다.
	 * @return
	 */
	public JsonObject getObject()
	{
		return curNode.getAsJsonObject();
	}
	
	
	/**
	 * 현재 노드를 root로 변경합니다.
	 * @param path
	 * @return
	 * @throws Exception
	 */
	public GsonHelper root()
	{
		curNode = root;
		return this;
	}
	
	
	/**
	 * 현재 노드를 path에 지정된 위치로 변경합니다. path는 상대경로입니다.
	 * @param path
	 * @return
	 * @throws Exception
	 */
	public GsonHelper on(String path) throws Exception
	{
		curNode = getElement(path);
		return this;
	}
	
	
	public Boolean contains(String path) {
		try {
			getElement(path);
			return true;
		}
		catch (Exception e) {
			return false;
		}
	}
	
	
	/**
	 * path에 지정된 위치의 값을 JsonElement로 가져옵니다.
	 * @param path
	 * @return
	 * @throws Exception
	 */
	public JsonElement getElement(String path) throws Exception
	{
		String splitter = "/";
		String[] paths = path.split(splitter);
		int startIdx = 0;
		
		
		if (paths.length == 0)
			return (curNode == null ? null : curNode.getAsJsonObject());

		//  '/'로 시작하는 경우 root부터 탐색한다.
		if (paths[0].length() == 0) {
			curNode = root;
			startIdx++;
		}
		

		JsonElement element = curNode.getAsJsonObject();
		for (int i = startIdx ; i < paths.length ; ++i)
		{
			try
			{
				if (element instanceof JsonArray) {
					Integer idx = Integer.parseInt(paths[i]);
					JsonArray item = (JsonArray)element;
					element = item.get(idx);
				}
				else {
					element = element.getAsJsonObject().get(paths[i]);
				}
				
				if (element == null)
					throw new Exception();
			}
			catch (Exception e)
			{
				throw new Exception(String.format("Invalid path(%s of %s) name.", paths[i], path));
			}
		}
		
		return element;
	}
	
	/**
	 * 현재 위치의 값을 Collection형식으로 가져옵니다.
	 * @return
	 */
	public <T> Collection<T> getArray()
	{
		Type type = (new TypeToken<Collection<T>>(){}).getType();
		Gson gson = new Gson();
		
		return gson.fromJson(curNode, type);
	}
	

	public <T> Collection<T> getArray(String path)
	{
		try {
			Type type = (new TypeToken<Collection<T>>(){}).getType();
			Gson gson = new Gson();
			
			return gson.fromJson(curNode, type);
		}
		catch (Exception e) {
			return null;
		}
	}
	
	
	public Boolean getBooleanValue() {
		return curNode.getAsBoolean();
	}
	public Boolean getBooleanValue(String path, Boolean defaultValue) throws Exception {
		try {
			return this.on(path).getBooleanValue();
		} catch (Exception e) {
			return defaultValue;
		}
	}
	
	
	public Integer getIntValue() {
		return curNode.getAsInt();
	}
	public Integer getIntValue(String path, Integer defaultValue) throws Exception {
		try {
			return this.on(path).getIntValue();
		} catch (Exception e) {
			return defaultValue;
		}
	}
	
	
	public Long getLongValue() {
		return curNode.getAsLong();
	}
	public Long getLongValue(String path, Long defaultValue) throws Exception {
		try {
			return this.on(path).getLongValue();
		} catch (Exception e) {
			return defaultValue;
		}
	}
	
	
	public Float getFloatValue() {
		return curNode.getAsFloat();
	}
	public Float getFloatValue(String path, Float defaultValue) throws Exception {
		try {
			return this.on(path).getFloatValue();
		} catch (Exception e) {
			return defaultValue;
		}
	}
	
	
	public Double getDoubleValue() {
		return curNode.getAsDouble();
	}
	public Double getDoubleValue(String path, Double defaultValue) throws Exception {
		try {
			return this.on(path).getDoubleValue();
		} catch (Exception e) {
			return defaultValue;
		}
	}
	
	
	public String getStringValue() {
		return curNode.getAsString();
	}
	public String getStringValue(String path, String defaultValue) throws Exception {
		try {
			return this.on(path).getStringValue();
		} catch (Exception e) {
			return defaultValue;
		}
	}
	
	
	public <T> T mappingTo(Class<T> targetClass) {
		return (new Gson()).fromJson(curNode, targetClass);
	}
}
