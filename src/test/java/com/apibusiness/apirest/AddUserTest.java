package com.apibusiness.apirest;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import com.apibusiness.apirest.entity.User;
import com.apibusiness.apirest.repository.UserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AddUserTest {
	
	@Autowired
	private UserRepository repository;
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	
	@Test
	public void createUser() {
		
		/*User user = new User();
		user.setPassword(encoder.encode("12345678"));
		user.setUser("security");
		user.setRoles("Admin");
		user.setAccountNonExpired(true);
		user.setAccountNonLocked(true);
		user.setCredentialsNonExpired(true);
		user.setEnabled(true);
		
		User response = repository.save(user);
		
		assertTrue(response.getPassword().equalsIgnoreCase(user.getPassword()));*/
		
	}
	
	@Test
	public void findByUserTest() {
		String name = "security";
		User response = repository.findByUser(name);
		assertTrue(response.getUser().equalsIgnoreCase(name));
		
	}
	
}
