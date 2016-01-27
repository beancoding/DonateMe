package com.dmcliver.donateme.domain;

import static java.util.Arrays.asList;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserDetailsImpl implements UserDetails {

	private static final long serialVersionUID = -6827543992702671386L;

	private String userName;
	private String password;
	private int role;
	private boolean enabled;

	public UserDetailsImpl(String userName, String password, int role, boolean enabled) {

		this.userName = userName;
		this.password = password;
		this.role = role;
		this.enabled = enabled;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return asList(new SimpleGrantedAuthority(Role.parse(role).toString()));
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return userName;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return this.enabled;
	}
}
