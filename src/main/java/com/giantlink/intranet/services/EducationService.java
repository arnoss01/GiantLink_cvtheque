package com.giantlink.intranet.services;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;

import com.giantlink.intranet.exceptions.GlAlreadyExistException;
import com.giantlink.intranet.exceptions.GlNotFoundException;
import com.giantlink.intranet.models.requests.EducationRequest;
import com.giantlink.intranet.models.responses.EducationResponse;

public interface EducationService {

	EducationResponse add(EducationRequest educationRequest) throws GlAlreadyExistException;

	List<EducationResponse> getAll();

	EducationResponse get(Long id) throws GlNotFoundException;

	void delete(Long id) throws GlNotFoundException;

	EducationResponse update(Long id, EducationRequest educationRequest) throws GlNotFoundException;

	Map<String, Object> getAllPaginations(String name, Pageable pageable);

}
