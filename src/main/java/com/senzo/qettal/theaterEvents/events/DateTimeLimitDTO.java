package com.senzo.qettal.theaterEvents.events;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
@JsonInclude(NON_NULL)
public class DateTimeLimitDTO {
	@JsonProperty(value="last_hours_limit")
	private Long lastHoursLimit;

	public Instant getDate() {
		return Instant.now().plus(lastHoursLimit, ChronoUnit.HOURS) ;
	}
	
}
