package com.signal.api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.signal.api.model.Region;

public interface RegionRepository extends JpaRepository<Region, Long>{
	
	Optional<Region> findByNomRegion(String nomRegion);
	Boolean existsByNomRegion(String nomRegion);
	List<Region> findByUsername(String username);
}
