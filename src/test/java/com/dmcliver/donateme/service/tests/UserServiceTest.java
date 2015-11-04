package com.dmcliver.donateme.service.tests;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

import org.hibernate.exception.ConstraintViolationException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.dmcliver.donateme.DuplicateException;

import static com.dmcliver.donateme.WebConstraints.Strings.BLANK;

import com.dmcliver.donateme.datalayer.UserDAO;
import com.dmcliver.donateme.domain.User;
import com.dmcliver.donateme.models.UserModel;
import com.dmcliver.donateme.services.UserServiceImpl;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

	@Mock private UserDAO userDAO;
	@Mock private PasswordEncoder encoder;
	
	@Test(expected = DuplicateException.class)
	public void save_WithConstraintViolation_ThrowsDuplicateException() throws DuplicateException, Exception {
		
		UserModel model = new UserModel();
		doThrow(new ConstraintViolationException(BLANK, null, BLANK)).when(userDAO).save(any(User.class));
		
		UserServiceImpl userService = new UserServiceImpl(userDAO, encoder);
		userService.save(model);
	}
	
	@Test
	public void save_WithGeneralException_RethrowsException() throws DuplicateException, Exception {
		
		UserModel model = new UserModel();
		doThrow(new Exception()).when(userDAO).save(any(User.class));
		
		UserServiceImpl userService = new UserServiceImpl(userDAO, encoder);
		
		try {
			userService.save(model);
		}
		catch(Exception ex) {
			assertTrue(!(ex instanceof ConstraintViolationException));
		}
	}
}
