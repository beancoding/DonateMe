package com.dmcliver.donateme.models;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

public class UserModel {

	private String password;
	private String name;
	private String firstName;
	private String lastName;
	private String confirmPassword;
	private String email;

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
	
	@Length(min = 2, message = "Please enter a proper first name")
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	@Length(min = 3, message = "Please enter a proper last name")
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	@NotBlank(message = "Please enter an email address")
	@Email(message = "Please enter a proper email address")
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
}
