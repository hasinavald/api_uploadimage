package com.signal.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.signal.api.model.ERole;
import com.signal.api.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long>{
	
	Optional<Role> findByName(ERole name);

}
