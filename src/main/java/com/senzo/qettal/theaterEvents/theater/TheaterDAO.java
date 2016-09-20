package com.senzo.qettal.theaterEvents.theater;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.senzo.qettal.theaterEvents.users.User;

@Component
public class TheaterDAO implements Theaters {

	@Autowired
	private EntityManager em;
	
	@Override
	@Transactional
	public Theater save(Theater theater) {
		em.persist(theater);
		return theater;
	}

	@Override
	@Transactional
	public void update(Theater theater) {
		em.merge(theater);
	}
	
	@Override
	public Optional<Theater> findById(Long id) {
		return Optional.ofNullable(em.find(Theater.class, id));
	}

	public List<Theater> from(User user) {
		return em.createQuery("from Theater where owner = :owner", Theater.class)
				.setParameter("owner", user)
				.getResultList();
	}


}
