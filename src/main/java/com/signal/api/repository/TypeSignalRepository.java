package com.signal.api.repository;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.signal.api.model.TypeSignal;

public interface TypeSignalRepository extends JpaRepository<TypeSignal, Long>{
	Optional<TypeSignal> findByType(String type);
	Boolean existsByType(String type);
	@Modifying
	@Transactional
	@Query(value = "UPDATE typesignals SET type = :type WHERE id = :id", nativeQuery = true)
	int updateTypeSignal(String type,Long id);
}
