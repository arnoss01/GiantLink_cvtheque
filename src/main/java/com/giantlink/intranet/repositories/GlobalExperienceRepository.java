package com.giantlink.intranet.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.giantlink.intranet.entities.Cv;
import com.giantlink.intranet.entities.GlobalExperience;

@Repository
public interface GlobalExperienceRepository extends JpaRepository<GlobalExperience, Long> {
	Optional<GlobalExperience> findByCampanyName(String name);
	
	@Modifying
    @Query("delete from GlobalExperience g where g.cv = ?1")
    void deleteByCv(Cv cv);

	Page<GlobalExperience> findByCampanyNameContainingIgnoreCase(String name, Pageable pageable);
}
