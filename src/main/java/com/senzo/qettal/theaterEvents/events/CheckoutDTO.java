package com.senzo.qettal.theaterEvents.events;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
@JsonIgnoreProperties(ignoreUnknown = true)

public class CheckoutDTO {
	@JsonProperty
	private Long quantity;

	public Long getQuantity() {
		return quantity;
	}
}
