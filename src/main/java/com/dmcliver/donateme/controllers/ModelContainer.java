package com.dmcliver.donateme.controllers;

import java.util.UUID;

public interface ModelContainer {

	void add(Object data, String key);
	<T> T get(String key);
	long getId(String idName);
	UUID getUUID(String uuidName);
}
