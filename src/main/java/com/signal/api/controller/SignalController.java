package com.signal.api.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.signal.api.model.Signal;
import com.signal.api.model.TypeSignal;
import com.signal.api.payload.response.MessageResponse;
import com.signal.api.repository.RegionRepository;
import com.signal.api.repository.SignalRepository;
import com.signal.api.repository.TypeSignalRepository;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/signal")
public class SignalController {
	
	@Autowired
	SignalRepository signalRepository;
	
	@Autowired
	RegionRepository regionRepository;
	
	@Autowired
	TypeSignalRepository typeSignalRepository;
	
	@GetMapping("/all")
	@PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
	public ResponseEntity<?> findAll() {
		return ResponseEntity.ok(signalRepository.findAll());
	}

	@GetMapping("/one/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> findById(@PathVariable("id") Long id) {
		return ResponseEntity.ok(signalRepository.findById(id));
	}
	
	//Ho an'ny Region ny moderator
	@GetMapping("/byregion")
	@PreAuthorize("hasRole('MODERATOR')")
	public ResponseEntity<?> getRegion(@RequestBody String username){
		return ResponseEntity.ok(regionRepository.findByUsername(username));
	}
	
	@GetMapping("/findByRegion")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> findByRegion(@Param("region") String region){
		return ResponseEntity.ok(signalRepository.findByRegion(region));
	}

	@GetMapping("/findbyuserpost")
	@PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR') or hasRole('USER')")
	public ResponseEntity<List<Signal>> findByUsername(@Param("usermane") String username){
		return ResponseEntity.ok(signalRepository.findByUsername(username));
	}
	
	@PutMapping("/update/{id}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
	public ResponseEntity<?> updateSignal(@PathVariable("id") Long id, @RequestBody String status){
		signalRepository.updateStatusSignal(status, id);
		return ResponseEntity.ok(new MessageResponse("Update successfully"));
	}

	@PutMapping("/updateRegion/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> updateRegionSignal(@PathVariable("id") Long id, @RequestBody String region){
		signalRepository.updateRegionSignal(region, id);
		return ResponseEntity.ok(new MessageResponse("Update successfully"));
	}
	
	@PostMapping("/postsignal")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<MessageResponse> uploadFile(
			@RequestParam("file") MultipartFile file, 
			@RequestParam("typeSignal") String typeSignal,
			@RequestParam("description") String description,
			@RequestParam("latitude") float latitude,
			@RequestParam("longitude") float longitude,
			@RequestParam("status") String status,
			@RequestParam("date") Date date,
			@RequestParam("username") String username
	) {
		Signal s = new Signal();
		
		List<TypeSignal> _type = new ArrayList<TypeSignal>();
		TypeSignal type = typeSignalRepository.findByType(typeSignal).get();
		_type.add(type);
		try {
			s.setImage(file.getBytes());
			s.setDescription(description);
			s.setTypeSignal(_type);
			s.setLatitude(latitude);
			s.setLongitude(longitude);
			s.setStatus(status);
			s.setDate(date);
			s.setUsername(username);
		} catch (IOException e) {
			e.printStackTrace();
		}
	    signalRepository.save(s);
	    return ResponseEntity.ok(new MessageResponse("Saved successfully"));
	}
	
	@DeleteMapping("/delete/{id}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	public ResponseEntity<MessageResponse> deleteSignal(@PathVariable Long id){
		signalRepository.deleteById(id);
		return ResponseEntity.ok(new MessageResponse("Signal remove successfully"));
	}
	
	

}
