package com.senzo.qettal.theaterEvents.events;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.senzo.qettal.theaterEvents.security.AllowUnloggedUsers;
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
	
	@Autowired
	private EventDTOConverter converter;
		
	@Autowired
	private EventsImageUploader uploader;
	
	@AllowUnloggedUsers
	@RequestMapping(path="/{id}", method = GET)
	public ResponseEntity<EventDTO> show(@PathVariable("id") Long eventId) {
		Optional<Event> optionalEvent =  events.withId(eventId);
		if(!optionalEvent.isPresent()){
			return new ResponseEntity<>(NOT_FOUND);
		}
		return new ResponseEntity<>(converter.from(optionalEvent.get()), OK) ;
	}
	
	@AllowUnloggedUsers
	@RequestMapping(path="/checkout/{id}", method = PUT)
	public ResponseEntity<EventDTO> checkout(@RequestBody CheckoutDTO checkout, @PathVariable("id") Long eventId) {
		Optional<Event> optionalEvent =  events.availableWithId(eventId, checkout.getQuantity());
		if(!optionalEvent.isPresent()){
			return new ResponseEntity<>(NOT_FOUND);
		}
		
		Event event = optionalEvent.get();
		event.removeFromStock(checkout.getQuantity(), events);
		
		return new ResponseEntity<>(converter.from(event), OK) ;
	}
	
	@AllowUnloggedUsers
	@RequestMapping(method = GET)
	public EventListDTO list(@RequestParam(name="hours_limit", required=false) Long hoursLimit, @RequestParam(name="q", required=false) String q) {
		List<Event> filteredEvents = events.all(hoursLimit, q);
		List<EventDTO> dtos = filteredEvents.stream().map(e -> converter.from(e)).collect(Collectors.toList());
		return new EventListDTO(dtos);
	}
	
	@RequestMapping(method = POST)
	public ResponseEntity<String> finish(@Validated @RequestBody EventDTO event){
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
		
		if(!optionalTheater.get().isOwnedBy(optionalUser.get()))
			return new ResponseEntity<String>(FORBIDDEN);
		
		event
			.toModel()
			.withTheater(optionalTheater.get())
			.save(events);
		
		return new ResponseEntity<String>(HttpStatus.CREATED);
	}
	
	@RequestMapping(path = "/image", method = POST)
	public ResponseEntity<ImageUploadResultDTO> upload(@RequestParam("image") MultipartFile file){
		ImageUploadResultDTO image= uploader.upload(file);
		return new ResponseEntity<>(image, HttpStatus.CREATED);
	}

	
	@RequestMapping(path = "/{eventId}", method = PUT)
	public ResponseEntity<String> update(@PathVariable("eventId") Long eventId, @Validated @RequestBody EventDTO eventDTO){
		Optional<TheaterDTO> optionalTheaterDTO = eventDTO.getTheaterDTO();
		if(!optionalTheaterDTO.isPresent())
			return new ResponseEntity<String>(BAD_REQUEST);
		
		Optional<Theater> optionalTheater = theaters.findById(optionalTheaterDTO.get().getId());
		if(!optionalTheater.isPresent())
			return new ResponseEntity<String>(NOT_FOUND);

		Optional<User> optionalUser = loggedUser.getUser();
		if(!optionalTheater.get().isOwnedBy(optionalUser.get()))
			return new ResponseEntity<String>(FORBIDDEN);
		
		Event event = eventDTO
			.toModel()
			.withTheater(optionalTheater.get());
		event.setId(eventId);
		
		events.update(event);
		
		return new ResponseEntity<String>(HttpStatus.OK);
	}
}
