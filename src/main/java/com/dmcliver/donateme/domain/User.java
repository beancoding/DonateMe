package com.dmcliver.donateme.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "User")
public class User {

	private long userId;
	private String firstName;
	private String secondName;
	private String userName;
	private String password;
	
	public User(String name, String password) {
		userName = name;
		this.password = password;
	}

	@Id
	@Column(name = "Id")
	@GenericGenerator(name = "UserId", strategy = "UserId")
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "UserId")
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	
	@Column(name = "FirstName")
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	@Column(name = "SecondName")
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
	
	@Column(name = "Password")
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
