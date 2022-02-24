package com.signal.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.signal.api.model.User;
import com.signal.api.payload.response.MessageResponse;
import com.signal.api.repository.UserRepository;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/user")
public class UserController {
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	PasswordEncoder encoder;
	
	@GetMapping("/all")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<List<User>> findAllUser(){
		return ResponseEntity.ok(userRepository.findAll());
	}
	
	@GetMapping("/findbyusername")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<User> findUser(@Param("username") String username){
		User user = userRepository.findByUsername(username).get();
		return ResponseEntity.ok(user);
	}
	
	@PutMapping("/updateuser/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<MessageResponse> updateUser(@PathVariable("id") Long id, @RequestBody User user){
		if(userRepository.getById(id) != null) {
			User _user = userRepository.getById(id);
			_user.setUsername(user.getUsername());
			_user.setEmail(user.getEmail());
			_user.setRegion(user.getRegion());
			userRepository.save(_user);
			return ResponseEntity.ok(new MessageResponse("User update successfully"));
		}else {
			return ResponseEntity.ok(new MessageResponse("User Not Found"));
		}
	}

	@PutMapping("/updatepassword/{id}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('MENDATOR')")
	public ResponseEntity<MessageResponse> updatePassword(@PathVariable("id") Long id, @RequestBody String password){
		if(password != null){
			userRepository.updatePassword(encoder.encode(password), id);
			return ResponseEntity.ok(new MessageResponse("Password update successful"));
		}else{
			return ResponseEntity.ok(new MessageResponse("Update failed"));
		}
	}
	
	@DeleteMapping("/deleteuser")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<MessageResponse> deleteUser(@Param("id") Long id){
		if(userRepository.getById(id) != null) {
			userRepository.deleteById(id);
			return ResponseEntity.ok(new MessageResponse("User delete successfully"));
		}else {
			return ResponseEntity.ok(new MessageResponse("User not found"));
		}
	}
}
