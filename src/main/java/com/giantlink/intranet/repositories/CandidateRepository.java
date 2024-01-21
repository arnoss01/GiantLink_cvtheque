package com.giantlink.intranet.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.giantlink.intranet.entities.Candidate;

@Repository
public interface CandidateRepository extends JpaRepository<Candidate, Long> {

	Optional<Candidate> findByEmail(String email);

	Page<Candidate> findByEmailContainingIgnoreCase(String email, Pageable pageable);
}
