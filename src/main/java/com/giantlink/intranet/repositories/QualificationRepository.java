package com.giantlink.intranet.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.giantlink.intranet.entities.Qualification;

@Repository
public interface QualificationRepository extends JpaRepository<Qualification, Long> {
	Optional<Qualification> findByName(String name);

	Page<Qualification> findByNameContainingIgnoreCase(String name, Pageable pageable);
}
