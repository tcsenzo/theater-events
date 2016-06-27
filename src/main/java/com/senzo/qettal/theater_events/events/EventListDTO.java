package com.senzo.qettal.theater_events.events;

import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;


@JsonSerialize
public class EventListDTO {

	@JsonProperty("events")
	private List<EventDTO> eventDTOs;
	
	/**
	 * @deprecated Use the factory method
	 */
	private EventListDTO() {
	}
	
	public EventListDTO(List<EventDTO> eventsDTOs) {
		eventDTOs = eventsDTOs;
	}

	public static EventListDTO from(List<Event> events) {
		return new EventListDTO(events.stream().map(e -> EventDTO.from(e)).collect(Collectors.toList()));
	}

	
}
