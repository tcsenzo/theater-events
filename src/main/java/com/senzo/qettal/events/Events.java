package com.senzo.qettal.events;

import java.util.List;

public interface Events {

	List<Event> all();

	void save(Event event);
}
