package com.senzo.qettal.theaterEvents.theater;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.senzo.qettal.theaterEvents.users.User;

@JsonSerialize
@JsonInclude(NON_NULL)
public class TheaterDTO {

	@JsonProperty
	private Long id;
	@JsonProperty
	private String name;
	@JsonProperty
	private AddressDTO address;

	/**
	 * @deprecated Jackson eyes only
	 */
	TheaterDTO() {
	}
	
	private TheaterDTO(String name, AddressDTO address) {
		this.name = name;
		this.address = address;
	}

	public static TheaterDTO from(Theater theater) {
		return new TheaterDTO(theater.getName(), AddressDTO.from(theater.getAddress()));
	}

	public Theater toModel(User owner) {
		Optional<Address> optionalAddress = Optional.ofNullable(address).map(AddressDTO::toModel);
		Theater theater = new Theater(name, optionalAddress.orElse(null), owner);
		theater.setId(id);
		return theater;
	}

}