package com.dmcliver.donateme.datalayer;

import com.dmcliver.donateme.domain.User;

public interface UserDAO {
	
	User findByUserName(String userName);
	void save(User user);
}
