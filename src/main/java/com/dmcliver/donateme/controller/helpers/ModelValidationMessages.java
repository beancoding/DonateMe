package com.dmcliver.donateme.controller.helpers;

import javax.faces.application.FacesMessage.Severity;

import com.dmcliver.donateme.RequestLocaleFaultCodes;

/**
 * Adds validation messages to JSF context from an error properties file
 * @author danielmcliver
 */
public interface ModelValidationMessages {

	void add(RequestLocaleFaultCodes faultCode);
	void add(RequestLocaleFaultCodes message, Severity level);
}