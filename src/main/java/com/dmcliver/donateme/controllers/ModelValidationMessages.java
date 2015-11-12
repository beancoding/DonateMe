package com.dmcliver.donateme.controllers;

import javax.faces.application.FacesMessage.Severity;

public interface ModelValidationMessages {

	void add(String key);

	void add(Severity level, String summaryKey, String detailKey);

	void add(String summaryKey, String detailKey);

}