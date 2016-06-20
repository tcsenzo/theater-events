package com.senzo.qettal.users;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
@JsonInclude(Include.NON_NULL)
public class UserDTO {

	@JsonProperty
	private String name;
	@JsonProperty
	private String email;
	@JsonProperty
	private String password;

	/**
	 * @deprecated Jackson eyes only
	 */
	UserDTO() {
	}
	
	public User toModel() {
		try {
			byte[] passwordBytes = password.getBytes("UTF-8");
			byte[] hashedPassword = MessageDigest.getInstance("SHA-256").digest(passwordBytes);
			return new User(name, email, new String(hashedPassword));
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			throw new RuntimeException("Coulndn't hash the password of the user " + email, e);
		}
	}

}