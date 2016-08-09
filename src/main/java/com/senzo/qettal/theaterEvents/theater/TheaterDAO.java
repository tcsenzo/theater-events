package com.senzo.qettal.theaterEvents.theater;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
	public Optional<Theater> findById(Long id) {
		return Optional.ofNullable(em.find(Theater.class, id));
	}

	@Override
	public List<Theater> all() {
		return em.createQuery("from Theater", Theater.class).getResultList();
	}

}
