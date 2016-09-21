package com.senzo.qettal.theaterEvents.events;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Optional;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Future;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

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
	@NotBlank
	@Size(min = 5)
	private String name;
	@JsonProperty(required = true)
	@NotBlank
	@Size(min = 10)
	private String description;
	@JsonProperty(required = true)
	private String image;
	@JsonProperty(required = true)
	@NotNull
	@DecimalMin(value = "0", inclusive = false)
	private BigDecimal price;
	@JsonProperty(value = "half_price", required = true)
	private BigDecimal halfPrice;
	@JsonProperty(value = "original_price", required = true)
	@NotNull
	@DecimalMin(value = "0", inclusive = false)
	private BigDecimal originalPrice;
	@JsonProperty(value = "available_quantity", required = true)
	@NotNull
	@Min(1)
	private Long availableQuantity;
	@NotNull
	@Future
	@JsonProperty(value = "scheduled_date", required = true)
	private Instant scheduledDate;
	@NotNull
	@JsonProperty(required = true)
	private TheaterDTO theater;

	/**
	 * @deprecated Jackson eyes only
	 */
	EventDTO() {
	}

	private EventDTO(Long id, String name, String description, String image, BigDecimal price, BigDecimal halfPrice,
			BigDecimal originalPrice, Long availableQuantity, Instant scheduledDate, TheaterDTO theater) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.image = image;
		this.price = price;
		this.halfPrice = halfPrice;
		this.originalPrice = originalPrice;
		this.availableQuantity = availableQuantity;
		this.scheduledDate = scheduledDate;
		this.theater = theater;
	}

	public static EventDTO from(Event event) {
		Theater theater = event.getTheater();
		return new EventDTO(event.getId(), event.getName(), event.getDescription(), event.getImage(), event.getPrice(),
				event.getHalfPrice(), event.getOriginalPrice(), event.getAvailableQuantity(), event.getScheduledDate(),
				TheaterDTO.withoutEvents(theater));
	}

	public EventWithoutTheater toModel() {
		return new EventWithoutTheater(id, name, description, price, originalPrice, availableQuantity, scheduledDate);
	}

	public static EventDTO withoutTheater(Event event) {
		return new EventDTO(event.getId(), event.getName(), event.getDescription(), event.getImage(), event.getPrice(),
				event.getHalfPrice(), event.getOriginalPrice(), event.getAvailableQuantity(), event.getScheduledDate(),
				null);
	}

	@JsonIgnore
	public Optional<TheaterDTO> getTheaterDTO() {
		return Optional.ofNullable(theater);
	}
}
