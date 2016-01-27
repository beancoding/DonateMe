package com.dmcliver.donateme.service.tests;

import com.dmcliver.donateme.datalayer.UserDAO;
import com.dmcliver.donateme.services.LoginUserDetailsService;

public class LoginUserDetailsServiceStub extends LoginUserDetailsService {

	public LoginUserDetailsServiceStub(UserDAO userDAO) {
		this.userDAO = userDAO;
	}
}
