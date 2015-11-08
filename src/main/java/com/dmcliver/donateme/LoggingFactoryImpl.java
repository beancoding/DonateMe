package com.dmcliver.donateme;

import static org.slf4j.LoggerFactory.getLogger;

import org.slf4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class LoggingFactoryImpl implements LoggingFactory {

	public Logger create(Class<?> classType) {
		return getLogger(classType);
	}
}
