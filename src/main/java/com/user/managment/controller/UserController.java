package com.user.managment.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
	
	private static final String USER="/user";
	
	
	@GetMapping(USER)
	public String getUser() {
	    return "Hello Docker World";
	  }

}
