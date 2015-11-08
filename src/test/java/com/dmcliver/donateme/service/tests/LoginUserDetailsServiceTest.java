package com.dmcliver.donateme.service.tests;

import static com.dmcliver.donateme.domain.Role.ADMIN;
import static com.dmcliver.tests.Assertions.assertThrows;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

import org.junit.Test;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.dmcliver.donateme.datalayer.UserDAO;
import com.dmcliver.donateme.domain.User;
import com.dmcliver.donateme.services.LoginUserDetailsService;

public class LoginUserDetailsServiceTest {

	@Test
	public void loadUserByUsername_WithNullUser_ThrowsUserNotFoundException() {
		
		UserDAO userDAO = mock(UserDAO.class);
		
		LoginUserDetailsService service = new LoginUserDetailsService();
		service.initiateUserDAO(userDAO);

		assertThrows(UsernameNotFoundException.class, "The user 'Simon' cannot be found", () -> service.loadUserByUsername("Simon"));
	}
	
	@Test
	public void loadUserByUsername_WithValidUser_ReturnsUserDetails() {
		
		final String username = "username";
		final String password = "SecR3t7";
		
		User expectedUser = new User(username, "User", "Surname", "uname@email.com", password);
		expectedUser.setRole(ADMIN.privilege());
		
		UserDAO userDAO = mock(UserDAO.class);
		when(userDAO.findByUserName(anyString())).thenReturn(expectedUser);
		
		LoginUserDetailsService service = new LoginUserDetailsService();
		service.initiateUserDAO(userDAO);
		
		UserDetails user = service.loadUserByUsername(username);
		
		assertThat(user.getPassword(), is(password));
		assertThat(user.getAuthorities().stream().findFirst().get().getAuthority(), is(ADMIN.toString()));
	}
}
