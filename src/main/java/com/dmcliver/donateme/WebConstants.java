package com.dmcliver.donateme;

import static java.util.UUID.randomUUID;

public class WebConstants {

	/**
	 * Unique model properties used for A.O.P test join-point cut to join on
	 * @see TestAspect
	 * @see ProductCoordinatorTest
	 */
	public static final String modelName = randomUUID().toString();
	public static final String description = randomUUID().toString();
	public static final String newCategory = randomUUID().toString();
	public static final String brand = randomUUID().toString();
	
	public static class Strings {
		
		public static final String BLANK = "";
		public static final String TREE_ROOT = "Root";
	}
	
	public static class Security {

		public static final String USER = "User";
		public static final String ADMIN = "Admin";
	}

	public static class LogMessages {
		
		public static final String LoggingOutWhenNotLoggedIn = "Some one is trying to log out, when not logged in";
		public static final String MalformedURLError = "The filename: %s is malformed and hence bad and invalid.";
	}
}
