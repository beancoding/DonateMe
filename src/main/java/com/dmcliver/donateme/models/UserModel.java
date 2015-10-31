package com.dmcliver.donateme.models;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

public class UserModel {

	private String password;
	private String name;
	private String confirmPassword;

	@NotBlank(message = "Password is required")
	@Length(min = 7, message = "Please enter a longer password")
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	@NotBlank(message = "User name is required")
	@Length(min = 7, message = "Please enter a longer name")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@NotBlank(message = "Password confirmation is required")
	@Length(min = 7, message = "Password confirmation does not match rules")
	public String getConfirmPassword() {
		return confirmPassword;
	}
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
}
