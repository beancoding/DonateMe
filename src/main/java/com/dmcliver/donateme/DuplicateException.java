package com.dmcliver.donateme;

public class DuplicateException extends Exception {

	private static final long serialVersionUID = 5671076522106334057L;
	
	public DuplicateException(Exception ex) {
		super(ex);
	}
}
