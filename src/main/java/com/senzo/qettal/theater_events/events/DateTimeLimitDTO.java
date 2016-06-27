package com.senzo.qettal.theater_events.events;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
@JsonInclude(NON_NULL)
public class DateTimeLimitDTO {
	@JsonProperty(value="last_hours_limit")
	private Long lastHoursLimit;

	public LocalDateTime getDate() {
		return LocalDateTime.now().plus(lastHoursLimit, ChronoUnit.HOURS) ;
	}
	
}
