package com.signal.api.controller;

import java.util.Optional;

import javax.validation.Valid;

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
import org.springframework.web.bind.annotation.RestController;

import com.signal.api.model.TypeSignal;
import com.signal.api.payload.response.MessageResponse;
import com.signal.api.repository.TypeSignalRepository;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/typesignal")
public class TypeSignalController {
	
	@Autowired
	TypeSignalRepository typeSignalRepository;
	
	@GetMapping("/all")
	@PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR') or hasRole('USER')")
	public ResponseEntity<?> findAll() {
		return ResponseEntity.ok(typeSignalRepository.findAll());
	}
	
	@GetMapping("/one")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> findByNomRegoin(@Param("type") String type){
		if(!typeSignalRepository.existsByType(type)) {
			return ResponseEntity.ok(new MessageResponse("Type Signal Not Found"));
		}
		return ResponseEntity.ok(typeSignalRepository.findByType(type));
	}
	
	@PostMapping("/save")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> save(@Valid @RequestBody TypeSignal typeSignal){
		if(typeSignalRepository.existsByType(typeSignal.getType())) {
			return ResponseEntity.ok(new MessageResponse("Erreur: Type signal déja existant"));
		}
		typeSignalRepository.save(typeSignal);
		return ResponseEntity.ok(new MessageResponse("Type signal saved succesfully"));
	}

	@PutMapping("/updateType/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<MessageResponse> updateType(@PathVariable long id, @RequestBody String type){
		if(type != null){
			typeSignalRepository.updateTypeSignal(type,id);
			return ResponseEntity.ok(new MessageResponse("Type signale update"));
		}else{
			return ResponseEntity.ok(new MessageResponse("Type signale not update"));
		}
	}
	
	@PutMapping("/update/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> update(@PathVariable("id") long id, @RequestBody TypeSignal typeSignal){
		Optional<TypeSignal> typeSignalData = typeSignalRepository.findById(id);
		if(typeSignalData.isPresent()) {
			TypeSignal _typeSignal = typeSignalData.get();
			_typeSignal.setType(typeSignal.getType());
			_typeSignal.setcouleur(typeSignal.getcouleur());
			typeSignalRepository.save(_typeSignal);
		}
		return ResponseEntity.ok(new MessageResponse("Type Signal update succesfully"));
	}
	
	@DeleteMapping("/delete")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> delete(@Param("id") long id){
		TypeSignal typeSignal = typeSignalRepository.getById(id);
		typeSignalRepository.delete(typeSignal);
		return ResponseEntity.ok(new MessageResponse("Type Signal delete successfully"));
	}

}
