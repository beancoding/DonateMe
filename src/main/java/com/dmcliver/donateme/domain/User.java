package com.dmcliver.donateme.domain;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

@Entity
@Table(name = "SystemUser")
public class User {

	private long userId;
	private String firstName;
	private String secondName;
	private String userName;
	private String password;
	private String email;
	private Role role;
	private boolean enabled;
	
	public User(String name, String firstName, String lastName, String email, String password) {
		
		this.userName = name;
		this.firstName = firstName;
		this.secondName = lastName;
		this.email = email;
		this.password = password;
		this.enabled = false;
	}
	
	protected User(){}

	@Id
	@Column(name = "UserId")
	@GeneratedValue(strategy = IDENTITY)
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	
	@Column(name = "FirstName", nullable = false)
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	@Column(name = "SecondName", nullable = false)
	public String getSecondName() {
		return secondName;
	}
	public void setSecondName(String secondName) {
		this.secondName = secondName;
	}
	
	@Column(name = "UserName", unique = true)
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	@Column(name = "Password", nullable = false)
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "Email", nullable = false)
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "Role")
	public int getRole() {
		return role.privilege();
	}
	public void setRole(int role) {
		this.role = Role.parse(role);
	}

	@Type(type = "yes_no")
	@Column(name = "Enabled")
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
}
