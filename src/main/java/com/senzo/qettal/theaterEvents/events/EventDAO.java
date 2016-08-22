package com.senzo.qettal.theaterEvents.events;

import static java.time.LocalDateTime.now;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
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
	@Transactional
	public void update(Event event) {
		em.merge(event);
	}

	@Override
	public Optional<Event> withId(Long id) {
		return Optional.ofNullable(em.find(Event.class, id));
	}

	@Override
	public Optional<Event> availableWithId(Long eventId, Long quantity) {
		try{
			String hql = "from Event e where e.id = :id and e.availableQuantity - :quantity >= 0 ";
			return Optional.of(em.createQuery(hql, Event.class)
					.setParameter("id", eventId)
					.setParameter("quantity", quantity)
					.getSingleResult());
		} catch (NoResultException e){
			return Optional.empty();
		}
	}

	@Override
	public List<Event> all(Long hoursLimit, String q) {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Event> query = builder.createQuery(Event.class);
		Root<Event> e = query.from(Event.class);
		List<Predicate> ps = new ArrayList<>();
		if(q != null){
			ps.add(builder.like(e.get("name"), "%"+q+"%"));
		}
		if(hoursLimit != null){
			ps.add(builder.lessThan(e.get("scheduledDate"), now().plus(hoursLimit, ChronoUnit.HOURS)));
		}
		TypedQuery<Event> typedQuery = em.createQuery(query.where(ps.toArray(new Predicate[ps.size()])));
		return typedQuery.getResultList();
	}

}
