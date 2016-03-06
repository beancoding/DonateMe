package com.dmcliver.donateme.controller.helpers;

import java.util.UUID;

public interface ModelContainer {

	/**
	 * Adds a particular model to the faces request map using the underlying HTTP request session 
	 * @param data - The model
	 */
	void add(Object data, String key);
	
	/** Gets an object using supplied key from the faces request map stored in the underlying HTTP request session */
	<T> T get(String key);
	
	/**
	 * Gets an id from the faces request parameter map using the HTTP request parameters in the URL i.e. http://localhost/index?key=val 
	 * @param idName The key or id name
	 * @return The id
	 */
	long getId(String idName);
	
	/**
	 * Gets an id from the faces request parameter map using the HTTP request parameters in the URL i.e. http://localhost/index?key=val 
	 * @param uuidName The key or UUID name
	 * @return The UUID
	 */
	UUID getUUID(String uuidName);
}

