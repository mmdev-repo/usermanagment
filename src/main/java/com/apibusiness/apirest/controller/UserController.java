package com.apibusiness.apirest.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.apibusiness.apirest.entity.User;
import com.apibusiness.apirest.repository.UserRepository;


@RestController
@PreAuthorize("authenticated")
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST})
public class UserController {
	
	public static final String USER = "/user";
	public static final String USERS = "/users";
	public static final String DELETE_USER = "/user/{id}";
	
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@PostMapping(value= USER)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public User createUser(@Valid @RequestBody User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return userRepository.save(user);
	}
	
	@DeleteMapping(value= DELETE_USER)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public void deleteUser(@Valid @RequestParam Integer id) {
		userRepository.deleteById(id);
	}
	
	@GetMapping(value= USERS)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public Iterable<User> getUserList(){
		return userRepository.findAll();
	}

}
