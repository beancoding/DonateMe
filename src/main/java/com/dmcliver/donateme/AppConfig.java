package com.dmcliver.donateme;

import java.beans.PropertyVetoException;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.mchange.v2.c3p0.ComboPooledDataSource;

@Configuration
@EnableTransactionManagement
@PropertySource("classpath:/config.properties")
public class AppConfig {

	@Autowired
	private Environment env;
	
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
		
		String dbTask = env.getProperty("db.hbm2ddl.auto", "");
		if(!"".equals(dbTask))
			props.setProperty("hibernate.hbm2ddl.auto", dbTask);
		
		props.setProperty("hibernate.show_sql", "true");
		return props;
	}

	@Bean
	public ComboPooledDataSource dataSource() throws PropertyVetoException {

		ComboPooledDataSource ds = new ComboPooledDataSource();
		ds.setJdbcUrl(env.getProperty("db.url"));
		ds.setDriverClass(env.getProperty("db.driver"));
		ds.setUser(env.getProperty("db.user"));
		ds.setPassword(env.getProperty("db.password"));
		return ds;
	}
}
