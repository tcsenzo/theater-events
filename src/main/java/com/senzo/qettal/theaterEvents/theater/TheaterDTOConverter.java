package com.senzo.qettal.theaterEvents.theater;

import static java.util.stream.Collectors.toList;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.senzo.qettal.theaterEvents.events.EventDTO;
import com.senzo.qettal.theaterEvents.events.EventDTOConverter;

@Component
public class TheaterDTOConverter {

	@Autowired
	private EventDTOConverter eventConverter;
	
	public TheaterDTO from(Theater theater) {
		List<EventDTO> events = theater.getEvents()
				.stream()
				.map(eventConverter::withoutTheater)
				.collect(toList());
		return new TheaterDTO(theater.getName(), AddressDTO.from(theater.getAddress()), theater.getId(), events);
	}
	
	public TheaterDTO withoutEvents(Theater theater) {
		return new TheaterDTO(theater.getName(), AddressDTO.from(theater.getAddress()), theater.getId(), null);
	}

}
