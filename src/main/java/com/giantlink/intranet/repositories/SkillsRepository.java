package com.giantlink.intranet.repositories;

import org.springframework.stereotype.Repository;

import com.giantlink.intranet.entities.Skills;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface SkillsRepository extends JpaRepository<Skills, Long>{
	Optional<Skills> findByName(String name); 

	Page<Skills> findByNameContainingIgnoreCase(String name, Pageable pageable);
}
