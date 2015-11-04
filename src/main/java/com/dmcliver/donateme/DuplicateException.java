package com.dmcliver.donateme;

public class DuplicateException extends Exception {

	public DuplicateException(Exception ex) {
		super(ex);
	}

	private static final long serialVersionUID = 5671076522106334057L;

}
