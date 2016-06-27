package com.senzo.qettal.theater_events.theater;

import static java.util.stream.Collectors.toList;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
public class TheaterListDTO {

	@JsonProperty("theaters")
	private List<TheaterDTO> theatersDTOs;

	/**
	 * @deprecated Use the factory method
	 */
	private TheaterListDTO() {
	}

	
	private TheaterListDTO(List<TheaterDTO> theatersDTOs) {
		this.theatersDTOs = theatersDTOs;
	}

	public static TheaterListDTO from(List<Theater> theaters) {
		return new TheaterListDTO(theaters.stream().map(TheaterDTO::from).collect(toList()));
	}

}
