package com.giantlink.intranet.services.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.giantlink.intranet.entities.Education;
import com.giantlink.intranet.exceptions.GlAlreadyExistException;
import com.giantlink.intranet.exceptions.GlNotFoundException;
import com.giantlink.intranet.mappers.EducationMapper;
import com.giantlink.intranet.models.requests.EducationRequest;
import com.giantlink.intranet.models.responses.EducationResponse;
import com.giantlink.intranet.repositories.EducationRepository;
import com.giantlink.intranet.services.EducationService;

@Service
public class EducationServiceImpl implements EducationService {

	@Autowired
	EducationRepository educationRepository;

	@Autowired
	EducationMapper educationMapper;

	@Transactional
	@Override
	public EducationResponse add(EducationRequest educationRequest) throws GlAlreadyExistException {
		Optional<Education> findEducation = educationRepository.findByName(educationRequest.getName());
		if (findEducation.isPresent()) {
			throw new GlAlreadyExistException("education", Education.class.getSimpleName());
		}
		Education education = EducationMapper.INSTANCE.EducationRequestToEducation(educationRequest);
		education.setCvs(new HashSet<>());

		return educationMapper.educationToEducationResponse(educationRepository.save(education));
	}

	@Override
	public List<EducationResponse> getAll() {
		return EducationMapper.INSTANCE.mapEducation(educationRepository.findAll());
	}

	@Override
	public EducationResponse get(Long id) throws GlNotFoundException {
		Optional<Education> education = educationRepository.findById(id);
		if (education.isEmpty()) {
			throw new GlNotFoundException("education", Education.class.getSimpleName());
		}
		return educationMapper.educationToEducationResponse(education.get());
	}

	@Override
	public void delete(Long id) throws GlNotFoundException {
		Optional<Education> education = educationRepository.findById(id);
		if (education.isEmpty()) {
			throw new GlNotFoundException("education", Education.class.getSimpleName());
		}
		educationRepository.deleteById(id);
	}

	@Override
	public EducationResponse update(Long id, EducationRequest educationRequest) throws GlNotFoundException {
		Optional<Education> education = educationRepository.findById(id);
		if (education.isEmpty()) {
			throw new GlNotFoundException("education", Education.class.getSimpleName());
		}
		education.get().setName(educationRequest.getName());
		education.get().setDescription(educationRequest.getDescription());
		return educationMapper.educationToEducationResponse(educationRepository.save(education.get()));
	}

	@Override
	public Map<String, Object> getAllPaginations(String name, Pageable pageable) {
		List<EducationResponse> educationResponses = new ArrayList<>();
		Page<Education> educations = (name.isBlank()) ? educationRepository.findAll(pageable)
				: educationRepository.findByNameContainingIgnoreCase(name, pageable);
		educations.getContent().forEach(education -> {
			EducationResponse response = EducationResponse.builder().id(education.getId())
					.description(education.getDescription()).name(education.getName()).build();

			educationResponses.add(response);
		});
		Map<String, Object> educationResponse = new HashMap<>();
		educationResponse.put("content", educationResponses);
		educationResponse.put("currentPage", educations.getNumber());
		educationResponse.put("totalElements", educations.getTotalElements());
		educationResponse.put("totalPages", educations.getTotalPages());
		return educationResponse;
	}

}
