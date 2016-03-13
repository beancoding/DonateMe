package com.dmcliver.donateme.services;

import com.dmcliver.donateme.DuplicateException;
import com.dmcliver.donateme.domain.User;
import com.dmcliver.donateme.models.UserModel;

public interface UserService {

	void save(UserModel model) throws DuplicateException, Exception;
	User getByUserName(String userName);
}
