package com.senzo.qettal.theaterEvents.events;

import java.math.BigDecimal;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
public class EventDTO {

	private String name;
	private String description;
	private BigDecimal price;
	
	/**
	 * @deprecated Jackson eyes only
	 */
	EventDTO() {
	}
	
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

	public Event toEvent() {
		return new Event(name, description, price);
	}
}
