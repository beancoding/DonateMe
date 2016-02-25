package com.dmcliver.tests;

import static com.dmcliver.donateme.RequestLocaleFaultCodes.ProductSaveError;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.util.Locale;

import org.junit.Test;

public class RequestLocaleFaultCodesTest {

	@Test
	public void findsFaultCodeOK() {
		
		String message = ProductSaveError.toString();
		
		assertNotNull(message);
		assertThat(message, is("Something went wrong trying to save the product, please try again a bit later to see if it succeeds"));
	}
	
	@Test
	public void localizesOK() {
		
		String localizedError = ProductSaveError.toLocale(Locale.FRENCH);
		assertThat(localizedError, is("Je Suis Error"));
	}
}

