package com.giantlink.intranet.services.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.giantlink.intranet.entities.Candidate;
import com.giantlink.intranet.entities.Cv;
import com.giantlink.intranet.entities.Domain;
import com.giantlink.intranet.entities.Education;
import com.giantlink.intranet.entities.GlobalExperience;
import com.giantlink.intranet.entities.Language;
import com.giantlink.intranet.entities.Skills;
import com.giantlink.intranet.exceptions.GlAlreadyExistException;
import com.giantlink.intranet.exceptions.GlNotFoundException;
import com.giantlink.intranet.mappers.CvMapper;
import com.giantlink.intranet.models.requests.CvRequest;
import com.giantlink.intranet.models.requests.CvSearch;
import com.giantlink.intranet.models.responses.CvResponse;
import com.giantlink.intranet.repositories.CandidacyRepository;
import com.giantlink.intranet.repositories.CandidateRepository;
import com.giantlink.intranet.repositories.CvRepository;
import com.giantlink.intranet.repositories.DomainRepository;
import com.giantlink.intranet.repositories.EducationRepository;
import com.giantlink.intranet.repositories.GlobalExperienceRepository;
import com.giantlink.intranet.repositories.LanguageRepository;
import com.giantlink.intranet.repositories.SkillsRepository;
import com.giantlink.intranet.services.CvService;
import com.giantlink.intranet.services.GlobalExperienceService;

@Service
public class CvServiceImpl implements CvService {

	@Autowired
	CvRepository cvRepository;

	@Autowired
	DomainRepository domainRepository;

	@Autowired
	SkillsRepository skillsRepository;

	@Autowired
	GlobalExperienceRepository experienceRepository;

	@Autowired
	EducationRepository educationRepository;

	@Autowired
	LanguageRepository languageRepository;

	@Autowired
	CandidacyRepository candidacyRepository;

	@Autowired
	CandidateRepository candidateRepository;
	
	@Autowired
	GlobalExperienceService experienceService;

	@Override
	public CvResponse add(CvRequest cvRequest) throws GlNotFoundException, GlAlreadyExistException {

		Optional<Candidate> candidate = candidateRepository.findById(cvRequest.getCandidateId());

		if (!candidate.isPresent())
			throw new GlNotFoundException(Candidate.class.getSimpleName(), "Candidate Not found !!");

		
		Cv cv = Cv.builder().availability(cvRequest.getAvailability()).comment(cvRequest.getComment())
				.candidate(candidate.get()).build();
		cvRepository.save(cv);
		
//		//GlobalExperiences
//		Set<GlobalExperience> experiences = new HashSet<>();
//		
//		for (int i = 0; i < cvRequest.getExperienceRequests().size(); i++) {
//			cvRequest.getExperienceRequests().get(i).setCvId(cv.getId());
//			experiences.add(experienceService.add(cvRequest.getExperienceRequests().get(i)));
//		}
//		cv.setGlobalExperiences(experiences);
//		
		// Domains
		cv.setDomains(new HashSet<Domain>());
		if (cvRequest.getDomainsId() != null && !cvRequest.getDomainsId().isEmpty()) {
			cvRequest.getDomainsId().forEach(c -> {
				Optional<Domain> findDomain = domainRepository.findById(c);
				cv.getDomains().add(findDomain.get());
			});
		}

		// Skills
		cv.setSkills(new HashSet<Skills>());

		if (cvRequest.getSkillsId() != null && !cvRequest.getSkillsId().isEmpty()) {
			cvRequest.getSkillsId().forEach(c -> {
				Optional<Skills> findSkill = skillsRepository.findById(c);
				cv.getSkills().add(findSkill.get());
			});
		}

		// Education
		cv.setEducations(new HashSet<Education>());

		if (cvRequest.getEducationsId() != null && !cvRequest.getEducationsId().isEmpty()) {
			cvRequest.getEducationsId().forEach(c -> {
				Optional<Education> findEducation = educationRepository.findById(c);
				cv.getEducations().add(findEducation.get());
			});
		}

		// Languague
		cv.setLanguages(new HashSet<Language>());

		if (cvRequest.getLanguagesId() != null && !cvRequest.getLanguagesId().isEmpty()) {
			cvRequest.getLanguagesId().forEach(c -> {
				Optional<Language> findLanguage = languageRepository.findById(c);
				cv.getLanguages().add(findLanguage.get());
			});
		}

		// Candidacy
		/*cv.setCandidacies(new HashSet<Candidacy>());

		if (cvRequest.getCandidaciesId() != null && !cvRequest.getCandidaciesId().isEmpty()) {
			cvRequest.getCandidaciesId().forEach(c -> {
				Optional<Candidacy> findCandidacy = candidacyRepository.findById(c);
				cv.getCandidacies().add(findCandidacy.get());
			});
		}*/

		return CvMapper.INSTANCE.cvToCvResponse(cvRepository.save(cv));
	}

	@Override
	public List<CvResponse> getAll() {
		List<CvResponse> allcv = new ArrayList<CvResponse>();

		cvRepository.findAll().forEach(av -> allcv.add(CvMapper.INSTANCE.cvToCvResponse(av)));

		return allcv;
	}

	@Override
	public CvResponse get(Long id) throws GlNotFoundException {

		Optional<Cv> findCv = cvRepository.findById(id);
		if (findCv.isEmpty()) {
			throw new GlNotFoundException("cv", Cv.class.getSimpleName());
		}

		return CvMapper.INSTANCE.cvToCvResponse(findCv.get());
	}

	@Override
	public void delete(Long id) throws GlNotFoundException {

		Optional<Cv> findCv = cvRepository.findById(id);
		if (findCv.isEmpty()) {
			throw new GlNotFoundException("cv", Cv.class.getSimpleName());
		}

		cvRepository.deleteById(id);

	}

	@Override
	public Map<String, Object> getAllPaginations(String availability, Pageable pageable) {

		List<CvResponse> cvResponses = new ArrayList<>();
		Page<Cv> cv = (availability.isBlank()) ? cvRepository.findAll(pageable)
				: cvRepository.findByAvailabilityContainingIgnoreCase(availability, pageable);
		cv.getContent().forEach(c -> {
			cvResponses.add(CvMapper.INSTANCE.cvToCvResponse(c));
		});
		Map<String, Object> cvResponse = new HashMap<>();
		cvResponse.put("content", cvResponses);
		cvResponse.put("currentPage", cv.getNumber());
		cvResponse.put("totalElements", cv.getTotalElements());
		cvResponse.put("totalPages", cv.getTotalPages());
		return cvResponse;
	}

	@Override
	public Map<String, Object> searchByEntities(CvSearch search, Pageable pageable) {
		
		Page<Cv> cvs = cvRepository.findByAvailabilityLikeAndDomainsNameLikeAndSkillsNameLikeAndEducationsNameLikeAndLanguagesNameLike(
				"%"+search.getAvailability() + "%", "%"+ search.getDomainName() + "%",
				"%"+ search.getSkillName() + "%", "%"+ search.getEducationName() + "%",
				"%"+ search.getLanguageName() + "%", pageable);
		
		List<CvResponse> cvResponses = new ArrayList<>();
		
		cvs.getContent().forEach(c -> {
			cvResponses.add(CvMapper.INSTANCE.cvToCvResponse(c));
		});
		Map<String, Object> cvResponse = new HashMap<>();
		cvResponse.put("content", cvResponses);
		cvResponse.put("currentPage", cvs.getNumber());
		cvResponse.put("totalElements", cvs.getTotalElements());
		cvResponse.put("totalPages", cvs.getTotalPages());
		return cvResponse;
	}
	
	@Override
	public List<CvResponse> getByEntity(String Entity, String name) {
		List<Cv> cvs = null;
		switch (Entity) {

		case "Domain":
			cvs = cvRepository.findByEducationsName(name);
			break;

		case "Skill":
			cvs = cvRepository.findBySkillsName(name);
			break;

		case "GlobalbExperience":
			cvs = cvRepository.findByGlobalExperiencesCampanyName(name);
			break;

		case "Education":
			cvs = cvRepository.findByEducationsName(name);
			break;

		case "Language":
			cvs = cvRepository.findByLanguagesName(name);
			break;

		case "Candidate":
			cvs = cvRepository.findByCandidateLastName(name);
			break;

		/*
		 * case "Candidacy": cvs = cvRepository.findByCandidaciesStatus(name); break;
		 */

		}

		List<CvResponse> cvResponse = CvMapper.INSTANCE.mapListCv(cvs);

		return cvResponse;
	}

}
