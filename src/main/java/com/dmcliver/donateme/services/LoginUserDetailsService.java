package com.dmcliver.donateme.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.dmcliver.donateme.datalayer.UserDAO;
import com.dmcliver.donateme.domain.User;
import com.dmcliver.donateme.domain.UserDetailsImpl;

@Service
public class LoginUserDetailsService implements UserDetailsService {

	@Autowired
	protected UserDAO userDAO;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		User user = userDAO.findByUserName(username);
		
		if(user == null)
			throw new UsernameNotFoundException("The user '" + username + "' cannot be found");
		
		return new UserDetailsImpl(user.getUserName(), user.getPassword(), user.getRole(), user.isEnabled());
	}
}
