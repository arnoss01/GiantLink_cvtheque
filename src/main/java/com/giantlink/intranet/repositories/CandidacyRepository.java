package com.giantlink.intranet.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.giantlink.intranet.entities.Candidacy;

@Repository
public interface CandidacyRepository extends JpaRepository<Candidacy, Long> {

	Page<Candidacy> findByApplicationNameContainingIgnoreCase(String name, Pageable pageable);

}
