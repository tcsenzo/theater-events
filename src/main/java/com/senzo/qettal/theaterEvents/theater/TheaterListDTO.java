package com.senzo.qettal.theaterEvents.theater;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
public class TheaterListDTO {

	@JsonProperty("theaters")
	private List<TheaterDTO> theatersDTOs;

	/**
	 * @deprecated Jackson eyes only
	 */
	TheaterListDTO() {
	}
	
	TheaterListDTO(List<TheaterDTO> theatersDTOs) {
		this.theatersDTOs = theatersDTOs;
	}

}
