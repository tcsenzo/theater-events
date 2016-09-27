package com.senzo.qettal.theaterEvents.theater;

import static java.util.stream.Collectors.toList;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.senzo.qettal.theaterEvents.security.LoggedUser;
import com.senzo.qettal.theaterEvents.users.User;

@RestController
@RequestMapping("/theaters")
public class TheaterController {

	@Autowired
	private Theaters theaters;
	@Autowired
	private LoggedUser loggedUser;
	@Autowired
	private TheaterDTOConverter converter;
	
	@RequestMapping(method = GET)
	public TheaterListDTO myTheaters() {
		List<Theater> myTheaters = theaters.from(loggedUser.getUser().get());
		return new TheaterListDTO(myTheaters.stream().map(converter::withoutEvents).collect(toList()));
	}
	
	@RequestMapping(method = POST)
	public ResponseEntity<String> create(@Valid @RequestBody TheaterDTO theater){
		Optional<User> optionalUser = loggedUser.getUser();
		
		theater
			.toModel(optionalUser.get())
			.findOrSave(theaters);
		
		return new ResponseEntity<String>(CREATED);
	}
	
	@RequestMapping(path="/{theaterId}", method = PUT)
	public ResponseEntity<String> update(@PathVariable("theaterId") Long theaterId, @Valid @RequestBody TheaterDTO theaterDTO){
		Optional<Theater> optionalTheater = theaters.findById(theaterId);
		if(!optionalTheater.isPresent())
			return new ResponseEntity<String>(NOT_FOUND);

		Optional<User> optionalUser = loggedUser.getUser();
		if(!optionalTheater.get().isOwnedBy(optionalUser.get()))
			return new ResponseEntity<String>(FORBIDDEN);

		
		Theater theater = theaterDTO
			.toModel(optionalUser.get());
		theater.setId(theaterId);
		theaters.update(theater);
		
		return new ResponseEntity<String>(OK);
	}
	
	@RequestMapping(path = "/{theaterId}", method = GET)
	public ResponseEntity<TheaterDTO> details(@PathVariable("theaterId") Long theaterId) {
		Optional<Theater> optionalTheater = theaters.findById(theaterId);
		if(!optionalTheater.isPresent()){
			return new ResponseEntity<>(NOT_FOUND);
		}
		return new ResponseEntity<>(converter.from(optionalTheater.get()), OK);
	}
}
