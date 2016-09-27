package com.senzo.qettal.theaterEvents.events;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.senzo.qettal.theaterEvents.theater.Theater;
import com.senzo.qettal.theaterEvents.theater.TheaterDTOConverter;

@Component
public class EventDTOConverter {
	
	@Autowired
	private EventImageUrlExtractor extractor;
	@Autowired
	private TheaterDTOConverter theaterConverter;
	
	public EventDTO from(Event event) {
		Theater theater = event.getTheater();
		return new EventDTO(event.getId(), event.getName(), event.getDescription(), extractor.extractFrom(event), event.getPrice(),
				event.getHalfPrice(), event.getOriginalPrice(), event.getAvailableQuantity(), event.getScheduledDate(),
				theaterConverter.withoutEvents(theater));
	}

	public EventDTO withoutTheater(Event event) {
		return new EventDTO(event.getId(), event.getName(), event.getDescription(), extractor.extractFrom(event), event.getPrice(),
				event.getHalfPrice(), event.getOriginalPrice(), event.getAvailableQuantity(), event.getScheduledDate(),
				null);
	}

}
