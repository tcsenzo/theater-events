package com.senzo.qettal.users;

import java.util.List;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserDAO implements Users {

	@Autowired
	private EntityManager em;
	
	@Override
	@Transactional
	public User save(User theater) {
		em.persist(theater);
		return theater;
	}

	@Override
	public List<User> all() {
		return em.createQuery("from Theater", User.class).getResultList();
	}

}
