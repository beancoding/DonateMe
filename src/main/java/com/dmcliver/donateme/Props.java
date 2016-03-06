package com.dmcliver.donateme;

import java.lang.reflect.Method;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.InvocationHandler;

/** Responsible for creates a proxy object from the given class that can be use to get the property name from a getter that was called on the proxy */
public class Props {
	
	private static String methodName;
	
	/** Creates a proxy object from the given class that can be use to get the property name from a getter that was called on the proxy */
	@SuppressWarnings("unchecked")
	public static <T> T from(Class<T> cls) {
		
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(cls);
		
		enhancer.setCallback(new InvocationHandler() {

			public Object invoke(Object obj, Method meth, Object[] args) throws Throwable {

				methodName = meth.getName();
				return null;
			}
		});
		
		return (T) enhancer.create();
	}
	
	/**
	 * @param methodReturnValue The value that was returned by the getter method that was called on the object 
	 * @return The property name as a string for the getter method that was called 
	 */
	public static String as(Object methodReturnValue) {
		
		if(methodName != null && !methodName.startsWith("get"))
			return methodName;
		
		if(methodName == null)
			return methodName;
		
		return methodName.substring(3, 4).toLowerCase() + methodName.substring(4);
	}
}
