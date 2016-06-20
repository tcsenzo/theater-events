package com.senzo.qettal.users;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private Users users;
	
	@RequestMapping(value = "/create", method = POST)
	public ResponseEntity<String> create(@Valid @RequestBody UserDTO user){
		user
			.toModel()
			.save(users);
		return new ResponseEntity<String>(HttpStatus.CREATED);
	}
}
