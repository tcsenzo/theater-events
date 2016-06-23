package com.senzo.qettal.events;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.senzo.qettal.security.LoggedUser;
import com.senzo.qettal.theater.Theater;
import com.senzo.qettal.theater.TheaterDTO;
import com.senzo.qettal.theater.Theaters;
import com.senzo.qettal.users.User;

@RestController
@RequestMapping("/events")
public class EventController {

	@Autowired
	private Events events;
	
	@Autowired
	private Theaters theaters;
	
	@Autowired
	private LoggedUser loggedUser;
	
	@RequestMapping(method = GET)
	public EventListDTO list() {
		return EventListDTO.from(events.all());
	}
	
	@RequestMapping(method = POST)
	public ResponseEntity<String> create(@Valid @RequestBody EventDTO event){
		Optional<TheaterDTO> optionalTheaterDTO = event.getTheaterDTO();
		if(!optionalTheaterDTO.isPresent())
			return new ResponseEntity<String>(BAD_REQUEST);
		
		Optional<User> optionalUser = loggedUser.getUser();
		
		Optional<Theater> optionalTheater = optionalTheaterDTO
			.get()
			.toModel(optionalUser.get())
			.findOrSave(theaters);
		
		if(!optionalTheater.isPresent())
			return new ResponseEntity<String>(NOT_FOUND);
		
		event
			.toModel()
			.withTheater(optionalTheater.get())
			.save(events);
		
		return new ResponseEntity<String>(HttpStatus.CREATED);
	}
}
