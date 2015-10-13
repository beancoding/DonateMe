package com.dmcliver.donateme;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class AppConfig {

	@Bean
	public HibernateTransactionManager transactionManager(){
		
		HibernateTransactionManager tx = new HibernateTransactionManager(sessionFactory().getObject());
		return tx;
	}

	@Bean
	public LocalSessionFactoryBean sessionFactory() {

		LocalSessionFactoryBean sessFac = new LocalSessionFactoryBean();
		return sessFac;
	}
}
