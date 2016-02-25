package com.dmcliver.donateme;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.faces.context.FacesContext;

public enum RequestLocaleFaultCodes {
	
	ProductImageSaveError, ProductSaveError, TestError, CategoryRequired, DuplicateUser;

	private static Locale locale;
	
	static {
		
		FacesContext jsfContext = FacesContext.getCurrentInstance();
		
		if(jsfContext != null && jsfContext.getExternalContext() != null) 
			locale = jsfContext.getExternalContext().getRequestLocale();
		else
			locale = Locale.getDefault();
	}
	
	private static ResourceBundle bundle = ResourceBundle.getBundle("ErrorMessages", locale);
	
	public String toString() {

		String key = super.toString();
		try {
			return bundle.getString(key);
		}
		catch (Exception e) {
			return key;
		}
	}
	
	public String toLocale(Locale l) {
		
		String key = super.toString();
		try {
			return ResourceBundle.getBundle("ErrorMessages", l).getString(key);
		}
		catch (Exception e) {
			return key;
		} 
	}
}
