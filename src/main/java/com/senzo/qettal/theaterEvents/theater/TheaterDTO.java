package com.senzo.qettal.theaterEvents.theater;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;
import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.senzo.qettal.theaterEvents.events.EventDTO;
import com.senzo.qettal.theaterEvents.users.User;

@JsonSerialize
@JsonInclude(NON_NULL)
public class TheaterDTO {

	@JsonProperty
	private Long id;
	@JsonProperty
	@NotBlank
	@Size(min = 3)
	private String name;
	@JsonProperty
	@Valid
	private AddressDTO address;
	@JsonProperty
	private List<EventDTO> events;

	/**
	 * @deprecated Jackson eyes only
	 */
	TheaterDTO() {
	}
	
	private TheaterDTO(String name, AddressDTO address, Long id, List<EventDTO> events) {
		this.name = name;
		this.address = address;
		this.id = id;
		this.events = events;
	}

	public static TheaterDTO from(Theater theater) {
		List<EventDTO> events = theater.getEvents()
				.stream()
				.map(EventDTO::withoutTheater)
				.collect(toList());
		return new TheaterDTO(theater.getName(), AddressDTO.from(theater.getAddress()), theater.getId(), events);
	}
	
	public static TheaterDTO withoutEvents(Theater theater) {
		return new TheaterDTO(theater.getName(), AddressDTO.from(theater.getAddress()), theater.getId(), null);
	}

	public Theater toModel(User owner) {
		Optional<Address> optionalAddress = Optional.ofNullable(address).map(AddressDTO::toModel);
		Theater theater = new Theater(name, optionalAddress.orElse(null), owner);
		theater.setId(id);
		return theater;
	}

	public Long getId() {
		return id;
	}

}