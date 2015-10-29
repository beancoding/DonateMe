package com.dmcliver.donateme.services;

import static java.util.Arrays.asList;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class CustomUserDetailsService implements UserDetailsService {

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		return new UserDetails() {

			private static final long serialVersionUID = 3766538852672117266L;

			@Override
			public Collection<? extends GrantedAuthority> getAuthorities() {
				return asList(new SimpleGrantedAuthority("Admin"));
			}

			@Override
			public String getPassword() {
				return "Password";
			}

			@Override
			public String getUsername() {
				return "Username";
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
				return true;
			}
		};
	}
}
