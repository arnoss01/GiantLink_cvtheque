package com.giantlink.intranet.services;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;

import com.giantlink.intranet.entities.GlobalExperience;
import com.giantlink.intranet.exceptions.GlAlreadyExistException;
import com.giantlink.intranet.exceptions.GlNotFoundException;
import com.giantlink.intranet.models.requests.GlobalExperienceRequest;
import com.giantlink.intranet.models.responses.GlobalExperienceResponse;

public interface GlobalExperienceService {

	GlobalExperience add(GlobalExperienceRequest globalExperienceRequest) throws GlAlreadyExistException;

	List<GlobalExperienceResponse> getAll();

	GlobalExperienceResponse get(Long id) throws GlNotFoundException;

	void delete(Long id) throws GlNotFoundException;

	GlobalExperienceResponse update(Long id, GlobalExperienceRequest globalExperienceRequest)
			throws GlNotFoundException;

	Map<String, Object> getAllPaginations(String name, Pageable pageable);
}
