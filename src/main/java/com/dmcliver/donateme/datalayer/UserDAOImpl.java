package com.dmcliver.donateme.datalayer;

import static org.hibernate.criterion.Restrictions.eq;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.dmcliver.donateme.domain.User;

@Repository
public class UserDAOImpl implements UserDAO {

	private static final Logger logger = LoggerFactory.getLogger(UserDAOImpl.class);
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	@Transactional
	public User findByUserName(String userName) {

		Session session = sessionFactory.getCurrentSession();
		return (User)session.createCriteria(User.class)
					  		.add(eq("userName", userName))
					  		.uniqueResult();
	}

	@Override
	@Transactional
	public void save(User user) throws Exception {
		
		Session session = sessionFactory.getCurrentSession();
		
		try {
			session.save(user);
		}
		catch(Exception ex) {
			
			logger.error("Could not save user", ex);
			throw ex;
		}
	}
}
