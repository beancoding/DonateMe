package com.dmcliver.donateme.controller.tests;

import static com.dmcliver.donateme.RequestLocaleFaultCodes.DuplicateUser;
import static org.mockito.Mockito.*;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.slf4j.Logger;
import org.springframework.validation.BindingResult;

import com.dmcliver.donateme.DuplicateException;
import com.dmcliver.donateme.LoggingFactory;
import com.dmcliver.donateme.WebConstants;
import com.dmcliver.donateme.controllers.AccountController;
import com.dmcliver.donateme.models.UserModel;
import com.dmcliver.donateme.services.UserService;

import org.mockito.runners.*;

@RunWith(MockitoJUnitRunner.class)
public class AccountControllerTest {

	@Mock private UserService userService;
	@Mock private BindingResult result;
	@Mock private LoggingFactory logFactory;
	
	@Test
	public void register_WithNoErrors_SavesUser() throws Exception {
		
		UserModel model = buildUserModel("Password");
		
		when(result.hasErrors()).thenReturn(false);
		
		AccountController controller = new AccountController(userService, logFactory);
		controller.register(model, result, Locale.getDefault());
		
		verify(userService).save(model);
	}
	
	@Test
	public void register_WithPasswordError_AddsRejectionErrorToModelAndDoesntSaveUser() throws Exception {
		
		UserModel model = buildUserModel("Sugar");

		when(result.hasErrors()).thenReturn(false);
		
		AccountController controller = new AccountController(userService, logFactory);
		controller.register(model, result, Locale.getDefault());
		
		verify(userService, times(0)).save(model);
		verify(result).reject(anyString(), anyString());
	}
	
	@Test
	public void register_WithValdiationError_DoesntSaveUser() throws Exception {
		
		UserModel model = buildUserModel("Password");
		
		when(result.hasErrors()).thenReturn(true);
		
		AccountController controller = new AccountController(userService, logFactory);
		controller.register(model, result, Locale.getDefault());
		
		verify(userService, times(0)).save(model);
	}
	
	@Test
	public void register_WithSaveError_AddsRejectionErrorAndReturnsToSamePage() throws Exception {
		
		final String errorCode = "DuplicateUser";
		Locale locale = Locale.getDefault();
		
		doThrow(new DuplicateException(null)).when(userService).save(any(UserModel.class));
		
		AccountController controller = new AccountController(userService, logFactory);
		controller.register(buildUserModel("Password"), result, locale);
		
		verify(result).reject(errorCode, DuplicateUser.toString());
	}
	
	@Test
	public void logout_WithNullSession_LogsMessageThatSessionIsNull() {
		
		Logger logger = mock(Logger.class);
		when(logFactory.create(any(Class.class))).thenReturn(logger);
		
		HttpServletRequest request = mock(HttpServletRequest.class);
		
		AccountController controller = new AccountController(userService, logFactory);
		controller.logout(request);

		verify(logger).info(WebConstants.LogMessages.LoggingOutWhenNotLoggedIn);
	}
	
	@Test
	public void logout_WithSession_DoesntLogMessage() {
		
		Logger logger = mock(Logger.class);
		when(logFactory.create(any(Class.class))).thenReturn(logger);
		
		HttpSession session = mock(HttpSession.class);
		
		HttpServletRequest request = mock(HttpServletRequest.class);
		when(request.getSession(false)).thenReturn(session);
		
		AccountController controller = new AccountController(userService, logFactory);
		controller.logout(request);

		verify(logger, never()).info(anyString());
	}
	
	private static UserModel buildUserModel(String confirmPassword) {
		
		UserModel model = new UserModel();
		model.setConfirmPassword(confirmPassword);
		model.setPassword("Password");
		return model;
	}
}
