package com.senzo.qettal.users;

import java.util.Optional;


public interface Users {
	User save(User user);
	Optional<User> findByEmail(String email);
}
