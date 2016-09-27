package com.senzo.qettal.theaterEvents.events;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;


@JsonSerialize
public class EventListDTO {

	@JsonProperty("events")
	private List<EventDTO> eventDTOs;
	
	/**
	 * @deprecated Jackson eyes only
	 */
	EventListDTO() {
	}
	
	EventListDTO(List<EventDTO> eventsDTOs) {
		eventDTOs = eventsDTOs;
	}
}
