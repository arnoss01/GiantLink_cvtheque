package com.giantlink.intranet.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.giantlink.intranet.entities.Domain;

@Repository
public interface DomainRepository extends JpaRepository<Domain, Long> {
	
	Optional<Domain> findByName(String name);
	
	Page<Domain> findByNameContainingIgnoreCase(String name, Pageable pageable);
}
