package com.senzo.qettal.theaterEvents.users;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="user")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String email;
	@Column(name="auth_id")
	private String authId;
	@Column(name="created_at")
	private LocalDateTime createdAt = LocalDateTime.now();

	/**
	 * @deprecated Hibernate eyes only
	 */
	User() {
	}
	
	public User(String name, String email, String authId) {
		this.name = name;
		this.email = email;
		this.authId = authId;
	}
	
	public String getEmail() {
		return email;
	}

	public Long getId() {
		return id;
	}
	
}
