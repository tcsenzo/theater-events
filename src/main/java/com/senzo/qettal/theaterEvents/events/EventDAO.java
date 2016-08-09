package com.senzo.qettal.theaterEvents.events;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
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

	@Override
	public Optional<Event> availableWithId(Long eventId) {
		try{
			String hql = "from Event e where e.id = :id and e.availableQuantity > 0 ";
			return Optional.of(em.createQuery(hql, Event.class)
					.setParameter("id", eventId)
					.getSingleResult());
		} catch (NoResultException e){
			return Optional.empty();
		}
	}

}
