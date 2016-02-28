package com.dmcliver.donateme;

/**
 * Common/custom type exception to wrap other non-checked exception up into a new checked exception
 */
public class CommonCheckedException extends Exception {

	private static final long serialVersionUID = 4137434459298793157L;

	/**
	 * Common/custom type exception to wrap other non-checked exception up into a new checked exception
	 */
	public CommonCheckedException(Exception ex) {
		super(ex);
	}
}
