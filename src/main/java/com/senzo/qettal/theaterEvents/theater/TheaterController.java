package com.senzo.qettal.theaterEvents.theater;

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

import com.senzo.qettal.theaterEvents.security.LoggedUser;
import com.senzo.qettal.theaterEvents.users.User;

@RestController
@RequestMapping("/theaters")
public class TheaterController {

	@Autowired
	private Theaters theaters;
	@Autowired
	private LoggedUser loggedUser;
	
	@RequestMapping(method = GET)
	public TheaterListDTO myTheaters() {
		return TheaterListDTO.from(theaters.from(loggedUser.getUser().get()));
	}
	
	@RequestMapping(method = POST)
	public ResponseEntity<String> create(@Valid @RequestBody TheaterDTO theater){
		Optional<User> optionalUser = loggedUser.getUser();
		
		theater
			.toModel(optionalUser.get())
			.findOrSave(theaters);
		
		return new ResponseEntity<String>(HttpStatus.CREATED);
	}
}
