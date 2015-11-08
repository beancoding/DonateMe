package com.dmcliver.donateme.services;

import java.util.Locale;

public interface ErrorMessageService {

	String get(String key, Locale locale);

}