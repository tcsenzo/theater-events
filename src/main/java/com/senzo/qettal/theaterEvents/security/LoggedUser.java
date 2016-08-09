package com.senzo.qettal.theaterEvents.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.senzo.qettal.theaterEvents.users.User;
import com.senzo.qettal.theaterEvents.users.Users;

@Component
public class LoggedUser {

	@Autowired
	private Users users;

	public Optional<User> getUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication instanceof AnonymousAuthenticationToken) {
			return Optional.empty();
		}
		return users.findByEmail(authentication.getName());
	}

}
