package com.dmcliver.donateme;

import static com.dmcliver.donateme.StringExt.BLANK;

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
	public HibernateTransactionManager transactionManager() throws PropertyVetoException {
		
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
		
		String dbTask = env.getProperty("db.hbm2ddl.auto", BLANK);
		if(!BLANK.equals(dbTask))
			props.setProperty("hibernate.hbm2ddl.auto", dbTask);
		
		props.setProperty("hibernate.show_sql", "true");
		return props;
	}

	@Bean
	public ComboPooledDataSource dataSource() throws PropertyVetoException {

		ComboPooledDataSource ds = new ComboPooledDataSource();
		ds.setJdbcUrl(env.getProperty("db.url", "jdbc:postgresql://localhost:5432/DonateMeDb"));
		ds.setDriverClass(env.getProperty("db.driver", "org.postgresql.Driver"));
		ds.setUser(env.getProperty("db.user", "postgres"));
		ds.setPassword(env.getProperty("db.password", "root"));
		return ds;
	}
}
