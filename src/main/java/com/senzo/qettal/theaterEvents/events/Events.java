package com.senzo.qettal.theaterEvents.events;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.security.access.prepost.PreAuthorize;

public interface Events {

	@PreAuthorize("#event.theater.owner.email == authentication.name")
	void save(Event event);

	public List<Event> thatWillHappenBefore(LocalDateTime limit);

	List<Event> all();

	Optional<Event> withId(Long eventId);

	Optional<Event> availableWithId(Long eventId);
}
