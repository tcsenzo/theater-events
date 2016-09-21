package com.senzo.qettal.theaterEvents.theater;

import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
@JsonInclude(Include.NON_NULL)
public class AddressDTO {
	
	@JsonProperty
	@NotBlank
	private String street;
	@JsonProperty
	@NotBlank
	private String number;
	@JsonProperty
	private String complement;
	@JsonProperty
	@NotBlank
	private String district;
	@JsonProperty
	@NotBlank
	private String city;
	@JsonProperty
	@NotBlank
	private String state;
	@JsonProperty
	@NotBlank
	private String country;
	@JsonProperty("zip_code")
	@NotBlank
	private String zipCode;

	/**
	 * @deprecated Jackson eyes only
	 */
	AddressDTO() {
	}
	
	private AddressDTO(String street, String number, String complement, String district, String city, String state, String country,
			String zipCode) {
		this.street = street;
		this.number = number;
		this.complement = complement;
		this.district = district;
		this.city = city;
		this.state = state;
		this.country = country;
		this.zipCode = zipCode;
	}

	public static AddressDTO from(Address address) {
		return new AddressDTO(address.getStreet(), address.getNumber(), address.getComplement(), address.getDistrict(), address.getCity(), address.getState(), address.getCountry(), address.getZipCode());
	}

	public Address toModel() {
		return new Address(street, number, complement, district, city, state, country, zipCode);
	}

}
