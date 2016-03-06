package com.dmcliver.donateme.tests;

import static org.hamcrest.CoreMatchers.is;

import org.junit.Assert;
import org.junit.Test;

import com.dmcliver.donateme.Props;
import com.dmcliver.donateme.domain.Product;

public class PropsTest {

	@Test
	public void getsPropertyNameOk() {
		
		Product prod = Props.from(Product.class);
		String modelText = Props.as(prod.getModel());

		Assert.assertThat(modelText, is("model"));
	}
}
