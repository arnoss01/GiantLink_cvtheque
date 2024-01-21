package com.giantlink.intranet.services.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.giantlink.intranet.entities.Cv;
import com.giantlink.intranet.entities.GlobalExperience;
import com.giantlink.intranet.exceptions.GlAlreadyExistException;
import com.giantlink.intranet.exceptions.GlNotFoundException;
import com.giantlink.intranet.mappers.GlobalExperienceMapper;
import com.giantlink.intranet.models.requests.GlobalExperienceRequest;
import com.giantlink.intranet.models.responses.GlobalExperienceResponse;
import com.giantlink.intranet.repositories.CvRepository;
import com.giantlink.intranet.repositories.GlobalExperienceRepository;
import com.giantlink.intranet.services.GlobalExperienceService;

@Service
public class GlobalExperienceServiceImpl implements GlobalExperienceService {

	@Autowired
	GlobalExperienceRepository globalExperienceRepository;

	@Autowired
	CvRepository cvRepository;

	@Autowired
	GlobalExperienceMapper experienceMapper;

	@Transactional
	@Override
	public GlobalExperience add(GlobalExperienceRequest globalExperienceRequest) throws GlAlreadyExistException {

		Optional<Cv> cvfind = cvRepository.findById(globalExperienceRequest.getCvId());
		if (!cvfind.isPresent()) {
			throw new GlAlreadyExistException("Cv", Cv.class.getSimpleName());
		}
		Cv cv = cvRepository.findById(globalExperienceRequest.getCvId()).get();

		GlobalExperience newExp = GlobalExperience.builder().campanyName(globalExperienceRequest.getCampanyName())
				.description(globalExperienceRequest.getDescription()).startDate(globalExperienceRequest.getStartDate())
				.endDate(globalExperienceRequest.getEndDate()).cv(cv).build();

		globalExperienceRepository.save(newExp);
		return newExp;
	}

	@Override
	public List<GlobalExperienceResponse> getAll() {
		return GlobalExperienceMapper.INSTANCE.mapGlobalExperience(globalExperienceRepository.findAll());
	}

	@Override
	public GlobalExperienceResponse get(Long id) throws GlNotFoundException {
		Optional<GlobalExperience> ge = globalExperienceRepository.findById(id);
		if (ge.isEmpty()) {
			throw new GlNotFoundException("global experience", GlobalExperience.class.getSimpleName());
		}
		return GlobalExperienceMapper.INSTANCE.globalExperienceToGlobalExperienceResponse(ge.get());
	}

	@Override
	public void delete(Long id) throws GlNotFoundException {
		globalExperienceRepository.deleteById(id);
	}

	@Override
	public GlobalExperienceResponse update(Long id, GlobalExperienceRequest globalExperienceRequest)
			throws GlNotFoundException {
		Optional<GlobalExperience> findGlobalExperience = globalExperienceRepository.findById(id);
		if (findGlobalExperience.isEmpty()) {
			throw new GlNotFoundException("global experience", GlobalExperience.class.getSimpleName());
		}
		findGlobalExperience.get().setCampanyName(globalExperienceRequest.getCampanyName());
		findGlobalExperience.get().setDescription(globalExperienceRequest.getDescription());
		globalExperienceRepository.save(findGlobalExperience.get());
		return GlobalExperienceMapper.INSTANCE.globalExperienceToGlobalExperienceResponse(findGlobalExperience.get());
	}

	@Override
	public Map<String, Object> getAllPaginations(String name, Pageable pageable) {
		List<GlobalExperienceResponse> globalExperienceResponses = new ArrayList<>();
		Page<GlobalExperience> experiences = (name.isBlank()) ? globalExperienceRepository.findAll(pageable)
				: globalExperienceRepository.findByCampanyNameContainingIgnoreCase(name, pageable);
		experiences.getContent().forEach(experiance -> {
			GlobalExperienceResponse response = GlobalExperienceResponse.builder().id(experiance.getId())
					.description(experiance.getDescription()).campanyName(experiance.getCampanyName()).build();

			globalExperienceResponses.add(response);
		});
		Map<String, Object> globalExperienceResponse = new HashMap<>();
		globalExperienceResponse.put("content", globalExperienceResponses);
		globalExperienceResponse.put("currentPage", experiences.getNumber());
		globalExperienceResponse.put("totalElements", experiences.getTotalElements());
		globalExperienceResponse.put("totalPages", experiences.getTotalPages());
		return globalExperienceResponse;
	}

}
