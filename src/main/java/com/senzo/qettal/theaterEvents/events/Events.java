package com.senzo.qettal.theaterEvents.events;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface Events {

	void save(Event event);

	void update(Event event);

	public List<Event> thatWillHappenBefore(LocalDateTime limit);

	List<Event> all();

	Optional<Event> withId(Long eventId);

	Optional<Event> availableWithId(Long eventId, Long quantity);

}
