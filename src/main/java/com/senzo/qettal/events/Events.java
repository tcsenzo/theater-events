package com.senzo.qettal.events;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;

public interface Events {

	List<Event> all();

	@PreAuthorize("#event.theater.owner.email == authentication.name")
	void save(Event event);
}
