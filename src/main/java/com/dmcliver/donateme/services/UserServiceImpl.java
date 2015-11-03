package com.dmcliver.donateme.services;

import static com.dmcliver.donateme.domain.Role.USER;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.dmcliver.donateme.datalayer.UserDAO;
import com.dmcliver.donateme.domain.User;
import com.dmcliver.donateme.models.UserModel;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDAO sysUserDAO;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public void save(UserModel model) {
		
		String password = passwordEncoder.encode(model.getPassword());
		User user = new User(model.getName(), model.getFirstName(), model.getLastName(), model.getEmail(), password);
		user.setRole(USER);
		sysUserDAO.save(user);
	}
}
