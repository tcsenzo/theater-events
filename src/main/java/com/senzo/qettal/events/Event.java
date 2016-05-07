package com.senzo.qettal.events;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.senzo.qettal.theater.Theater;

@Entity
@Table(name="event")
public class Event {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String description;
	private BigDecimal price;
	@Column(name="scheduled_date")
	private LocalDateTime scheduledDate;
	@Column(name="created_at")
	private LocalDateTime createdAt = LocalDateTime.now();
	@ManyToOne
	@JoinColumn(name="theater_id")
	private Theater theater;
	
	
	/**
	 * @deprecated Hibernate eyes only
	 */
	Event() {
	}
	
	public Event(String name, String description, BigDecimal price, LocalDateTime scheduledDate, Theater theater) {
		this.name = name;
		this.description = description;
		this.price = price;
		this.scheduledDate = scheduledDate;
		this.theater = theater;
	}

	public String getName() {
		return this.name;
	}

	public String getDescription() {
		return this.description;
	}

	public BigDecimal getPrice() {
		return this.price;
	}

	public LocalDateTime getScheduledDate() {
		return scheduledDate;
	}

	public Event save(Events events) {
		events.save(this);
		return this;
	}

	public Theater getTheater() {
		return theater;
	}

}
