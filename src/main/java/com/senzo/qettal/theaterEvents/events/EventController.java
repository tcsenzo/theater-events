package com.senzo.qettal.theaterEvents.events;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/events")
public class EventController {

	@Autowired
	private Events events;
	
	@RequestMapping("/")
	public EventListDTO list() {
		return EventListDTO.from(events.all());
	}
	
	@RequestMapping(value = "/create", method = POST)
	public void create(@Valid @RequestBody EventDTO event){
		events.save(event.toEvent());
	}
}
