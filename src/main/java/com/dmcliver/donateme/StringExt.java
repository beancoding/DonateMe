package com.dmcliver.donateme;

import java.io.IOException;

import org.codehaus.jackson.map.ObjectMapper;

public class StringExt {
	
	public static final String BLANK = "";
	
	public static boolean isNullOrEmpty(String text) {
		return text == null || BLANK.equals(text);
	}
	
	public static String mapObjToJSON(Object obj) {

		String jsonObj = "";
		try {
			jsonObj = new ObjectMapper().writeValueAsString(obj);
		}
		catch (IOException e) {}
		
		return jsonObj;
	}
	
	public static String concat(String... text) {
		
		String all = BLANK;
		
		for (String someText : text) 
			all += someText;
		
		return all;
	}
}
