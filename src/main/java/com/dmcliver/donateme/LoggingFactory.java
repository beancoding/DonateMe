package com.dmcliver.donateme;

import org.slf4j.Logger;

public interface LoggingFactory {

	Logger create(Class<?> classType);

}