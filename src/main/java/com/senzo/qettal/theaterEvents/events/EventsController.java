package com.senzo.qettal.theaterEvents.events;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EventsController {

	@Autowired
	private Events events;
	
	@RequestMapping("/")
	public EventListDTO list() {
		return EventListDTO.from(events.all());
	}
}
