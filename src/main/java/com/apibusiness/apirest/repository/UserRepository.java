package com.apibusiness.apirest.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import com.apibusiness.apirest.entity.User;

@Service
public interface UserRepository extends CrudRepository<User, Integer> {
	
	public User findByUser(String user);

}
