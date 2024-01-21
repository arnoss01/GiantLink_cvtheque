package com.giantlink.intranet.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.giantlink.intranet.entities.Language;

@Repository
public interface LanguageRepository extends JpaRepository<Language, Long> {
	Optional<Language> findByName(String name);

	Page<Language> findByNameContainingIgnoreCase(String name, Pageable pageable);
}
