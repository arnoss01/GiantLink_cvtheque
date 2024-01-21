package com.giantlink.intranet.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.giantlink.intranet.entities.Cv;

@Repository
public interface CvRepository extends JpaRepository<Cv, Long> {

	Optional<Cv> findByAvailability(String availability);

	Page<Cv> findByAvailabilityContainingIgnoreCase(String availability, Pageable pageable);

	List<Cv> findByCandidateLastName(String Lastname);

	List<Cv> findByDomainsName(String DomainName);

	List<Cv> findBySkillsName(String SkillName);

	List<Cv> findByGlobalExperiencesCampanyName(String GlobalExperienceName);

	List<Cv> findByEducationsName(String EducationName);

	List<Cv> findByLanguagesName(String LanguageName);
	
	Page<Cv> findByAvailabilityLikeAndDomainsNameLikeAndSkillsNameLikeAndEducationsNameLikeAndLanguagesNameLike(
			String availability , String DomainName,String SkillName,
			String EducationName,String LanguageName, 
			Pageable pageable
			);
}
