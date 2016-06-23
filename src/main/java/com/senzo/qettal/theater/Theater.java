package com.senzo.qettal.theater;

import java.time.LocalDateTime;
import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.senzo.qettal.users.User;


@Entity
@Table(name="theater")
public class Theater {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	@Embedded
	private Address address;
	@Column(name="created_at")
	private LocalDateTime createdAt = LocalDateTime.now();
	@ManyToOne
	@JoinColumn(name="owner_id")
	private User owner;
	
	/**
	 * @deprecated Hibernate eyes only
	 */
	Theater() {
	}
	
	public Theater(String name, Address address, User owner) {
		this.name = name;
		this.address = address;
		this.owner = owner;
	}

	public Address getAddress() {
		return address;
	}
	
	public String getName() {
		return name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public User getOwner() {
		return owner;
	}
	
	public Optional<Theater> findOrSave(Theaters theaters) {
		if(id == null){
			return Optional.of(theaters.save(this));
		}
		return theaters.findById(id);
	}
	
}
