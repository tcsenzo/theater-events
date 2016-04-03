package com.senzo.qettal.theaterEvents.events;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
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
	
	public static EventListDTO from(List<Event> events) {
		EventListDTO eventListDTO = new EventListDTO();
		eventListDTO.addAll(events);
		return eventListDTO;
	}

	private void addAll(List<Event> events) {
		eventDTOs = events.stream().map(e -> new EventDTO(e.getName(), e.getDescription(), e.getPrice())).collect(Collectors.toList());
	}
	
	public List<EventDTO> getEventDTOs() {
		return eventDTOs;
	}
	
	@JsonSerialize
	class EventDTO {

		private String name;
		private String description;
		private BigDecimal price;
		
		public EventDTO(String name, String description, BigDecimal price) {
			this.name = name;
			this.description = description;
			this.price = price;
		}

		public String getName() {
			return name;
		}
		
		public String getDescription() {
			return description;
		}
		
		public BigDecimal getPrice() {
			return price;
		}
	}

}
