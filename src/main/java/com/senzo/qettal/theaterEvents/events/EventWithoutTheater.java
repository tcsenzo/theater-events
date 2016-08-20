package com.senzo.qettal.theaterEvents.events;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.senzo.qettal.theaterEvents.theater.Theater;

public class EventWithoutTheater {

	private String name;
	private String description;
	private Long availableQuantity;
	private BigDecimal price;
	private LocalDateTime scheduledDate;
	private BigDecimal originalPrice;

	public EventWithoutTheater(String name, String description, BigDecimal price, BigDecimal originalPrice, Long availableQuantity, LocalDateTime scheduledDate) {
		this.name = name;
		this.description = description;
		this.originalPrice = originalPrice;
		this.availableQuantity = availableQuantity;
		this.price = price;
		this.scheduledDate = scheduledDate;
	}

	public Event withTheater(Theater theater) {
		return new Event(name, description, price, originalPrice, availableQuantity,  scheduledDate, theater);
	}

}
