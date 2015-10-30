package com.dmcliver.donateme;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.dmcliver.donateme.services.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.authorizeRequests()
			.antMatchers("/login", "/badlogin", "/register", "/logout", "/resources/**").permitAll()
			.antMatchers("/admin", "/admin/**").hasRole("Admin")
			.anyRequest().authenticated()
			.and()
			.formLogin().loginPage("/login").loginProcessingUrl("/jsecuritycheck").failureUrl("/badlogin")
			.permitAll()
			.and()
			.logout().logoutUrl("/logout");
	}
	
	@Override
	public void configure(AuthenticationManagerBuilder builder) throws Exception{
		
		builder.userDetailsService(userDetailsService())
			   .passwordEncoder(passwordEncoder());
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10);
		return encoder;
	}

	@Bean
	@Override
	public UserDetailsService userDetailsService(){
		return new CustomUserDetailsService();
	}
}
