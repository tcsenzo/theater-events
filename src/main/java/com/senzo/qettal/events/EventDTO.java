package com.senzo.qettal.events;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.senzo.qettal.theater.Theater;
import com.senzo.qettal.theater.TheaterDTO;

@JsonSerialize
public class EventDTO {

	@JsonProperty(required=true)
	private String name;
	@JsonProperty(required=true)
	private String description;
	@JsonProperty(required=true)
	private BigDecimal price;
	@JsonProperty(value="scheduled_date", required=true)
	private LocalDateTime scheduledDate;
	@JsonProperty(required=true)
	private TheaterDTO theater;
	
	/**
	 * @deprecated Jackson eyes only
	 */
	EventDTO() {
	}
	
	private EventDTO(String name, String description, BigDecimal price, LocalDateTime scheduledDate, TheaterDTO theater) {
		this.name = name;
		this.description = description;
		this.price = price;
		this.scheduledDate = scheduledDate;
		this.theater = theater;
	}
	
	public static EventDTO from(Event event) {
		Theater theater = event.getTheater();
		return new EventDTO(event.getName(), event.getDescription(), event.getPrice(), event.getScheduledDate(), TheaterDTO.from(theater));
	}

	public EventWithoutTheater toModel() {
		return new EventWithoutTheater(name, description, price, scheduledDate);
	}

	@JsonIgnore
	public Optional<TheaterDTO> getTheaterDTO() {
		return Optional.ofNullable(theater);
	}
}
