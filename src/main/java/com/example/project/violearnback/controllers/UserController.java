package com.example.project.violearnback.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.project.violearnback.entities.User;
import com.example.project.violearnback.repositories.UserRepository;

import jakarta.validation.Valid;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	UserRepository repository;
	
	
	@PostMapping
	public ResponseEntity<User> creteUser(@RequestBody @Valid User newUser) {
		return new ResponseEntity<>(repository.save(newUser), HttpStatus.CREATED);
	}
	
	@GetMapping
	public ResponseEntity<List<User>>  findAll(){
		return new ResponseEntity<>(repository.findAll(), HttpStatus.OK);
	}
	
	@GetMapping("/{userId}")
	public ResponseEntity<User> getById(@PathVariable String userId) {
		User user = repository.findByUserId(userId);
		
//		if (user.isEmpty()) {
//			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//		}
		return new ResponseEntity<>(user, HttpStatus.OK);
	}
	


	
	
	
	@DeleteMapping("/{userId}")
	public ResponseEntity<?> deleteUser(@PathVariable String  userId ){
		Optional<User> user = Optional.ofNullable(repository.findByUserId(userId));
		
		if(user.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		repository.delete(user.get());
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PutMapping("/{userId}")
	public ResponseEntity<User> updateUser(@PathVariable String userId, @RequestBody User updatedUser){
		Optional<User> user = Optional.ofNullable(repository.findByUserId(userId));
		
		if (user.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		
		updatedUser.setId(user.get().getId());
		return new ResponseEntity<>(repository.save(updatedUser), HttpStatus.OK);
	}
	
	
	

}
