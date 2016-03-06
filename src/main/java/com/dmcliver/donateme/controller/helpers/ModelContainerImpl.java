package com.dmcliver.donateme.controller.helpers;

import static java.lang.Long.parseLong;
import static javax.faces.context.FacesContext.getCurrentInstance;

import java.util.Map;
import java.util.UUID;

import javax.faces.context.ExternalContext;

import org.springframework.stereotype.Component;

@Component
public class ModelContainerImpl implements ModelContainer {

	/** {@inheritDoc} */
	@Override
	public void add(Object data, String key) {
		getRequestMap().put(key, data);
	}
	
	/** {@inheritDoc} */
	@Override
	@SuppressWarnings("unchecked")
	public <T> T get(String key) {
		return (T)getRequestMap().get(key);
	}

	/** {@inheritDoc} */
	@Override
	public long getId(String idName) {
		
		String value = getRequestParameterMap().get(idName);
		return value == null ? -1 : parseLong(idName);
	}
	
	/** {@inheritDoc} */
	@Override
	public UUID getUUID(String uuidName) {
		
		String value = getRequestParameterMap().get(uuidName);
		return value == null ? null : UUID.fromString(value);
	}
	
	private static Map<String, Object> getRequestMap() {
		return getExternalContext().getRequestMap();
	}
	
	private static Map<String, String> getRequestParameterMap() {
		return getExternalContext().getRequestParameterMap();
	}
	
	private static ExternalContext getExternalContext() {
		return getCurrentInstance().getExternalContext();
	}
}
