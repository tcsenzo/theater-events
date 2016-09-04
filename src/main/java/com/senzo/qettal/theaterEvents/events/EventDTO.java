package com.senzo.qettal.theaterEvents.events;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.senzo.qettal.theaterEvents.theater.Theater;
import com.senzo.qettal.theaterEvents.theater.TheaterDTO;

@JsonSerialize
@JsonInclude(NON_NULL)
public class EventDTO {

	@JsonProperty(required = true)
	private Long id;
	@JsonProperty(required = true)
	private String name;
	@JsonProperty(required = true)
	private String description;
	@JsonProperty(required = true)
	private BigDecimal price;
	@JsonProperty(value="half_price", required = true)
	private BigDecimal halfPrice;
	@JsonProperty(value="original_price", required = true)
	private BigDecimal originalPrice;
	@JsonProperty(value = "available_quantity", required = true)
	private Long availableQuantity;
	@JsonProperty(value = "scheduled_date", required = true)
	private LocalDateTime scheduledDate;
	@JsonProperty(required = true)
	private TheaterDTO theater;

	/**
	 * @deprecated Jackson eyes only
	 */
	EventDTO() {
	}

	private EventDTO(Long id, String name, String description, BigDecimal price, BigDecimal halfPrice, BigDecimal originalPrice, Long availableQuantity, LocalDateTime scheduledDate,
			TheaterDTO theater) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.price = price;
		this.halfPrice = halfPrice;
		this.originalPrice = originalPrice;
		this.availableQuantity = availableQuantity;
		this.scheduledDate = scheduledDate;
		this.theater = theater;
	}

	public static EventDTO from(Event event) {
		Theater theater = event.getTheater();
		return new EventDTO(event.getId(), event.getName(), event.getDescription(), event.getPrice(), event.getHalfPrice(),
				event.getOriginalPrice(), event.getAvailableQuantity(), event.getScheduledDate(), TheaterDTO.from(theater));
	}

	public EventWithoutTheater toModel() {
		return new EventWithoutTheater(name, description, price, originalPrice, availableQuantity, scheduledDate);
	}
	
	public static EventDTO withoutTheater(Event event) {
		return new EventDTO(event.getId(), event.getName(), event.getDescription(), event.getPrice(), event.getHalfPrice(),
				event.getOriginalPrice(), event.getAvailableQuantity(), event.getScheduledDate(), null);
	}

	@JsonIgnore
	public Optional<TheaterDTO> getTheaterDTO() {
		return Optional.ofNullable(theater);
	}
}
