package com.senzo.qettal.theaterEvents.events;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EventDAO implements Events{

	@Autowired
	private EntityManager em;
	
	@Override
	public List<Event> all() {
		return Arrays.asList(new Event("peca marota", "uma peca suber pacana, vc vai amar", new BigDecimal("200.00")));
	}

}
