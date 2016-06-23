package com.senzo.qettal.events;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;

public interface Events {

	@PreAuthorize("#event.theater.owner.email == authentication.name")
	void save(Event event);

	public List<Event> thatWillHappenUntil(LocalDateTime limit);

	List<Event> all();
}
