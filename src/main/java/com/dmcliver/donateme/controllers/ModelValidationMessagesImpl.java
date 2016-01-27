package com.dmcliver.donateme.controllers;

import static javax.faces.application.FacesMessage.SEVERITY_INFO;
import static javax.faces.context.FacesContext.getCurrentInstance;

import java.util.Locale;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dmcliver.donateme.ErrorMessageLocator;

@Component
public class ModelValidationMessagesImpl implements ModelValidationMessages {

	@Autowired
	private ErrorMessageLocator locator;

	public void add(String key) {
		
		String message = locator.get(key, getCurrentInstance().getExternalContext().getRequestLocale());
		getCurrentInstance().addMessage(null, new FacesMessage(message));
	}
	
	public void add(Severity level, String summaryKey, String detailKey) {
		
		Locale requestLocale = getCurrentInstance().getExternalContext().getRequestLocale();
		String summary = locator.get(summaryKey, requestLocale);
		String detail = locator.get(detailKey, requestLocale);
		getCurrentInstance().addMessage(null, new FacesMessage(level, summary, detail));
	}
	
	public void add(String summaryKey, String detailKey) {
		add(SEVERITY_INFO, summaryKey, detailKey);
	}
}
