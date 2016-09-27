package com.senzo.qettal.theaterEvents.events;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;


@JsonSerialize
@JsonInclude(NON_NULL)
public class ImageUploadResultDTO {
	@JsonProperty(required = true)
	private String image;

	/**
	 * @deprecated Jackson eyes only
	 */
	ImageUploadResultDTO() {
	}
	
	public ImageUploadResultDTO(String image) {
		this.image = image;
	}
	
	
}
