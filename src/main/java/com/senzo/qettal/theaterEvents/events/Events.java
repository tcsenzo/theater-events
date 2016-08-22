package com.senzo.qettal.theaterEvents.events;

import java.util.List;
import java.util.Optional;

public interface Events {

	void save(Event event);

	void update(Event event);

	List<Event> all(Long hoursLimit, String q);

	Optional<Event> withId(Long eventId);

	Optional<Event> availableWithId(Long eventId, Long quantity);

}
