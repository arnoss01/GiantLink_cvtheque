package com.giantlink.intranet.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.giantlink.intranet.entities.Education;

@Repository
public interface EducationRepository extends JpaRepository<Education, Long> {
	Optional<Education> findByName(String name);

	Page<Education> findByNameContainingIgnoreCase(String name, Pageable pageable);
}
