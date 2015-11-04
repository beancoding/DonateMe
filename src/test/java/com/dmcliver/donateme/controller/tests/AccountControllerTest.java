package com.dmcliver.donateme.controller.tests;

import static org.mockito.Mockito.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.validation.BindingResult;

import com.dmcliver.donateme.controllers.AccountController;
import com.dmcliver.donateme.models.UserModel;
import com.dmcliver.donateme.services.UserService;

import org.mockito.runners.*;

@RunWith(MockitoJUnitRunner.class)
public class AccountControllerTest {

	@Mock private UserService userService;
	@Mock private BindingResult result;
	
	@Test
	public void register_WithNoErrors_SavesUser() throws Exception {
		
		UserModel model = buildUserModel("Password");
		
		when(result.hasErrors()).thenReturn(false);
		
		AccountController controller = new AccountController(userService);
		controller.register(model, result);
		
		verify(userService).save(model);
	}
	
	@Test
	public void register_WithPasswordError_AddsRejectionErrorToModelAndDoesntSaveUser() throws Exception {
		
		UserModel model = buildUserModel("Sugar");

		when(result.hasErrors()).thenReturn(false);
		
		AccountController controller = new AccountController(userService);
		controller.register(model, result);
		
		verify(userService, times(0)).save(model);
		verify(result).reject(anyString(), anyString());
	}
	
	@Test
	public void register_WithValdiationError_DoesntSaveUser() throws Exception {
		
		UserModel model = buildUserModel("Password");
		
		when(result.hasErrors()).thenReturn(true);
		
		AccountController controller = new AccountController(userService);
		controller.register(model, result);
		
		verify(userService, times(0)).save(model);
	}
	
	private static UserModel buildUserModel(String confirmPassword) {
		
		UserModel model = new UserModel();
		model.setConfirmPassword(confirmPassword);
		model.setPassword("Password");
		return model;
	}
}
