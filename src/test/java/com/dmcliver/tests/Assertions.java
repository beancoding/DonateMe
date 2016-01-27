package com.dmcliver.tests;

import static com.dmcliver.donateme.WebConstants.Strings.BLANK;

public class Assertions {
	
	public static void assertThrows(Class<? extends Throwable> expectionType, String message, AssertThrowsAction action) {
		
		try {
			
			action.assertThrows();
			throw new AssertionError("Expected exception of type " + expectionType + " to be thrown, but no exception was thrown");
		}
		catch(Exception ex) {
			
			if(ex.getClass().equals(expectionType) && ex.getMessage().equals(message))
				assert true;
			else if (ex.getClass().equals(expectionType)) {
			
				String actualMessage = ex.getMessage();
				
				if(BLANK.equals(message))
					message = "<String.Empty>";
				if(BLANK.equals(ex.getMessage()))
					actualMessage = "<String.Empty>";
				
				throw new AssertionError("Expected message of '" + message + "' but got '" + actualMessage + "'");
			}
			else
				throw new AssertionError("Expected exception of type " + expectionType + " but got " + ex.getClass());
		}
	}
}