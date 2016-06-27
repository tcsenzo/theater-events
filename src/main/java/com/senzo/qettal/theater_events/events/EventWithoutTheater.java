package com.senzo.qettal.theater_events.events;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.senzo.qettal.theater_events.theater.Theater;

public class EventWithoutTheater {

	private String name;
	private String description;
	private BigDecimal price;
	private LocalDateTime scheduledDate;

	public EventWithoutTheater(String name, String description, BigDecimal price, LocalDateTime scheduledDate) {
		this.name = name;
		this.description = description;
		this.price = price;
		this.scheduledDate = scheduledDate;
	}

	public Event withTheater(Theater theater) {
		return new Event(name, description, price, scheduledDate, theater);
	}

}
