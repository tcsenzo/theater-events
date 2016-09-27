package com.senzo.qettal.theaterEvents.events;

import java.math.BigDecimal;
import java.time.Instant;

import com.senzo.qettal.theaterEvents.theater.Theater;

public class EventWithoutTheater {

	private String name;
	private String description;
	private Long availableQuantity;
	private BigDecimal price;
	private Instant scheduledDate;
	private BigDecimal originalPrice;
	private Long id;
	private String image;

	public EventWithoutTheater(Long id, String name, String description, String image, BigDecimal price, BigDecimal originalPrice, Long availableQuantity, Instant scheduledDate) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.image = image;
		this.originalPrice = originalPrice;
		this.availableQuantity = availableQuantity;
		this.price = price;
		this.scheduledDate = scheduledDate;
	}

	public Event withTheater(Theater theater) {
		Event event = new Event(name, description, image, price, originalPrice, availableQuantity,  scheduledDate, theater);
		event.setId(id);
		return event;
	}

}
