package com.dmcliver.donateme.controller.helpers;

import static javax.faces.context.FacesContext.getCurrentInstance;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;

import org.springframework.stereotype.Component;

import com.dmcliver.donateme.RequestLocaleFaultCodes;

/**
 * Adds validation messages to JSF context from an error properties file
 * @author danielmcliver
 */
@Component
public class ModelValidationMessagesImpl implements ModelValidationMessages {

	
	public void add(RequestLocaleFaultCodes message) {
		getCurrentInstance().addMessage(null, new FacesMessage(message.toString()));
	}
	
	public void add(RequestLocaleFaultCodes message, Severity level) {
	
		String messageText = message.toString();
		getCurrentInstance().addMessage(null, new FacesMessage(level, messageText, messageText));
	}
}
