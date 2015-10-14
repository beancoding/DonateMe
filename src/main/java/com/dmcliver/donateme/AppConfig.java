package com.dmcliver.donateme;

import java.beans.PropertyVetoException;
import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.mchange.v2.c3p0.ComboPooledDataSource;

@Configuration
@EnableTransactionManagement
public class AppConfig {

	@Bean
	public HibernateTransactionManager transactionManager() throws PropertyVetoException{
		HibernateTransactionManager tx = new HibernateTransactionManager(sessionFactory().getObject());
		return tx;
	}

	@Bean
	public LocalSessionFactoryBean sessionFactory() throws PropertyVetoException {

		LocalSessionFactoryBean sessFac = new LocalSessionFactoryBean();
		sessFac.setPackagesToScan("com.dmcliver.donateme.domain");
		sessFac.setDataSource(dataSource());
		sessFac.setHibernateProperties(properties());
		return sessFac;
	}

	public Properties properties() {

		Properties props = new Properties();
		props.setProperty("hibernate.hbm2ddl.auto", "create");
		props.setProperty("hibernate.show_sql", "true");
		return props;
	}

	@Bean
	public ComboPooledDataSource dataSource() throws PropertyVetoException {

		ComboPooledDataSource ds = new ComboPooledDataSource();
		ds.setJdbcUrl("jdbc:postgresql://localhost:5432/DonateMeDb");
		ds.setDriverClass("org.postgresql.Driver");
		ds.setUser("postgres");
		ds.setPassword("root");
		return ds;
	}
}
