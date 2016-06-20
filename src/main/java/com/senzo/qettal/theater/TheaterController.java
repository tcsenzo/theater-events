package com.senzo.qettal.theater;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/theaters")
public class TheaterController {

	@Autowired
	private Theaters theaters;
	
	
	@RequestMapping(method = GET)
	public TheaterListDTO list() {
		return TheaterListDTO.from(theaters.all());
	}
	
	@RequestMapping(method = POST)
	public ResponseEntity<String> create(@Valid @RequestBody TheaterDTO theater){
		theater
			.toModel()
			.findOrSave(theaters);
		return new ResponseEntity<String>(HttpStatus.CREATED);
	}
}
