package com.dmcliver.donateme;

import java.util.Locale;

public interface ErrorMessageLocator {

	String get(String key, Locale locale);

}