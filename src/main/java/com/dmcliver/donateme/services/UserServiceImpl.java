package com.dmcliver.donateme.services;

import static com.dmcliver.donateme.domain.Role.USER;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.dmcliver.donateme.DuplicateException;
import com.dmcliver.donateme.datalayer.UserDAO;
import com.dmcliver.donateme.domain.User;
import com.dmcliver.donateme.models.UserModel;

@Service
public class UserServiceImpl implements UserService {

	private UserDAO sysUserDAO;
	private PasswordEncoder passwordEncoder;

	@Autowired
	public UserServiceImpl(UserDAO userDAO, PasswordEncoder passwordEncoder) {

		sysUserDAO = userDAO;
		this.passwordEncoder = passwordEncoder;
	}
	
	@Override
	public void save(UserModel model) throws DuplicateException, Exception {
		
		String password = passwordEncoder.encode(model.getPassword());
		
		User user = new User(model.getName(), model.getFirstName(), model.getLastName(), model.getEmail(), password);
		user.setRole(USER.privilege());
		
		try {
			sysUserDAO.save(user);
		} 
		catch (Exception ex) {
			
			if(ex instanceof ConstraintViolationException)
				throw new DuplicateException(ex);
			else
				throw ex;
		}
	}
}
