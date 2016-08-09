package com.senzo.qettal.theaterEvents.events;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.senzo.qettal.theaterEvents.security.LoggedUser;
import com.senzo.qettal.theaterEvents.theater.Theater;
import com.senzo.qettal.theaterEvents.theater.TheaterDTO;
import com.senzo.qettal.theaterEvents.theater.Theaters;
import com.senzo.qettal.theaterEvents.users.User;

@RestController
@RequestMapping("/events")
public class EventController {

	@Autowired
	private Events events;
	
	@Autowired
	private Theaters theaters;
	
	@Autowired
	private LoggedUser loggedUser;
	
	@RequestMapping(path="/{id}", method = GET)
	public ResponseEntity<EventDTO> show(@PathVariable("id") Long eventId) {
		Optional<Event> optionalEvent =  events.withId(eventId);
		if(!optionalEvent.isPresent()){
			return new ResponseEntity<>(NOT_FOUND);
		}
		return new ResponseEntity<>(EventDTO.from(optionalEvent.get()), OK) ;
	}
	
	
	@RequestMapping(path="/checkout/{id}", method = GET)
	public ResponseEntity<EventDTO> showForCheckout(@PathVariable("id") Long eventId) {
		Optional<Event> optionalEvent =  events.availableWithId(eventId);
		if(!optionalEvent.isPresent()){
			return new ResponseEntity<>(NOT_FOUND);
		}
		return new ResponseEntity<>(EventDTO.from(optionalEvent.get()), OK) ;
	}
	
	@RequestMapping(method = GET)
	public EventListDTO list(@RequestParam(name="hours_limit", required=false) Long hoursLimit) {
		List<Event> filteredEvents;
		if(hoursLimit == null ){
			filteredEvents = events.all();
		} else {
			filteredEvents = events.thatWillHappenBefore(LocalDateTime.now().plus(hoursLimit, ChronoUnit.HOURS));
		}
		return EventListDTO.from(filteredEvents);
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
