package com.senzo.qettal.theater_events.events;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EventDAO implements Events{

	@Autowired
	private EntityManager em;
	
	@Override
	@Transactional
	public void save(Event event) {
		em.persist(event);
	}

	@Override
	public List<Event> thatWillHappenBefore(LocalDateTime limit) {
		return em.createQuery("from Event where scheduledDate < :limit", Event.class)
				.setParameter("limit", limit)
				.getResultList();
	}

	@Override
	public List<Event> all() {
		return em.createQuery("from Event", Event.class)
				.getResultList();
	}

	@Override
	public Optional<Event> withId(Long id) {
		return Optional.ofNullable(em.find(Event.class, id));
	}

}
