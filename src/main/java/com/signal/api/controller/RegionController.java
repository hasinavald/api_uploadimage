package com.signal.api.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.signal.api.model.Region;
import com.signal.api.payload.response.MessageResponse;
import com.signal.api.repository.RegionRepository;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/region")
public class RegionController {
	
	@Autowired
	RegionRepository regionRepository;
	
	@GetMapping("/all")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> findAll() {
		return ResponseEntity.ok(regionRepository.findAll());
	}
	
	@GetMapping("/one")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> findByNomRegoin(@Param("nomRegion") String nomRegion){
		if(!regionRepository.existsByNomRegion(nomRegion)) {
			return ResponseEntity.ok(new MessageResponse("Region Not Found"));
		}
		return ResponseEntity.ok(regionRepository.findByNomRegion(nomRegion));
	}
	
	@GetMapping("/findByUser")
	@PreAuthorize("hasRole('MODERATOR')")
	public ResponseEntity<?> findByUserRegion(@Param("username") String username){
		return ResponseEntity.ok(regionRepository.findByUsername(username));
	}
	
	@PostMapping("/save")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> save(@Valid @RequestBody Region region){
		if(regionRepository.existsByNomRegion(region.getNomRegion())) {
			return ResponseEntity.ok(new MessageResponse("Erreur: Region déja existant"));
		}
		regionRepository.save(region);
		return ResponseEntity.ok(new MessageResponse("Region saved succesfully"));
	}
	
	@PutMapping("/update")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> update(@Param("id") long id, @RequestBody Region region){
		if(region != null){
			regionRepository.save(region);
			return ResponseEntity.ok(new MessageResponse("Region update succesfully"));
		}else{
			return ResponseEntity.ok(new MessageResponse("Region not update"));
		}
	}
	
	@DeleteMapping("/delete")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> delete(@Param("id") long id){
		Region r = regionRepository.getById(id);
		regionRepository.delete(r);
		return ResponseEntity.ok(new MessageResponse("Region delete successfully"));
	}
}
