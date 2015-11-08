package com.dmcliver.donateme.services;

import static java.util.ResourceBundle.getBundle;

import java.util.Locale;
import java.util.MissingResourceException;

import org.springframework.stereotype.Service;

@Service
public class ErrorMessageServiceImpl implements ErrorMessageService {

	public String get(String key, Locale locale) {
		
		try {
			return getBundle("ErrorMessage", locale).getString(key);
		}
		catch (MissingResourceException ex) {
			return key;
		}
	}
}
