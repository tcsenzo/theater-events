package com.senzo.qettal.theaterEvents.events;

import java.util.List;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EventDAO implements Events{

	@Autowired
	private EntityManager em;
	
	@Override
	public List<Event> all() {
		return em.createQuery("from Event", Event.class).getResultList();
	}

	@Override
	@Transactional
	public void save(Event event) {
		em.persist(event);
	}

}
