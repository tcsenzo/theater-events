package com.senzo.qettal.theaterEvents.theater;

import java.util.List;
import java.util.Optional;

import com.senzo.qettal.theaterEvents.users.User;

public interface Theaters {
	Theater save(Theater theater);
	Optional<Theater> findById(Long id);
	List<Theater> from(User user);
}
