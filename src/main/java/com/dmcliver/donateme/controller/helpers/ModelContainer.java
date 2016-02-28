package com.dmcliver.donateme.controller.helpers;

import java.util.UUID;

public interface ModelContainer {

	/**
	 * Adds a particular model to the faces request map
	 * @param data - The model
	 * @param key
	 */
	void add(Object data, String key);
	
	<T> T get(String key);
	long getId(String idName);
	UUID getUUID(String uuidName);
}

