package com.dmcliver.donateme.domain.tests;

import static com.dmcliver.donateme.domain.Role.ADMIN;
import static com.dmcliver.donateme.domain.Role.USER;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Test;

import com.dmcliver.donateme.domain.Role;

public class RoleTest {

	@Test
	public void parse() {
		
		Role admin = Role.parse(1);
		Role user = Role.parse(2);
		Role badRole = Role.parse(5);
		
		assertThat(admin.toString(), is(ADMIN.toString()));
		assertThat(user.toString(), is(USER.toString()));
		assertNull(badRole);
	}
}
