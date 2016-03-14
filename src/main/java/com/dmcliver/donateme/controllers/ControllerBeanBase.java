package com.dmcliver.donateme.controllers;

import javax.faces.context.FacesContext;

import com.dmcliver.donateme.Overridable;

public abstract class ControllerBeanBase {
	
	@Overridable
	protected String getPrincipalUserName() {
		
		FacesContext jsfCtx = FacesContext.getCurrentInstance();
		
		if(jsfCtx != null && jsfCtx.getExternalContext() != null && jsfCtx.getExternalContext().getUserPrincipal() != null)
			return jsfCtx.getExternalContext().getUserPrincipal().getName();
		
		return null;
	}
}
