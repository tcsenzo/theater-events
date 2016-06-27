package com.senzo.qettal.theater_events.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;

@Configuration
public class PasswordEncoderProducer {

	@Bean
	public PasswordEncoder getPasswordEncoder() {
		return new StandardPasswordEncoder();
	}
	
}
