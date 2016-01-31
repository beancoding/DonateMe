package com.dmcliver.donateme;

public class StringExt {
	
	public static final String BLANK = "";
	
	public static boolean isNullOrEmpty(String text) {
		return text == null || BLANK.equals(text);
	}
}
