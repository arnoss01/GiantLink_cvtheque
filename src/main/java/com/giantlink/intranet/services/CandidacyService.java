package com.giantlink.intranet.services;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;

import com.giantlink.intranet.exceptions.GlAlreadyExistException;
import com.giantlink.intranet.exceptions.GlNotFoundException;
import com.giantlink.intranet.models.requests.CandidacyRequest;
import com.giantlink.intranet.models.responses.CandidacyResponse;

public interface CandidacyService {

	CandidacyResponse add(CandidacyRequest postulationRequest) throws GlAlreadyExistException, GlNotFoundException;

	CandidacyResponse get(Long id) throws GlNotFoundException;

	void delete(Long id) throws GlNotFoundException;

	CandidacyResponse update(Long id, CandidacyRequest postulationRequest) throws GlNotFoundException;

	List<CandidacyResponse> responses();

	Map<String, Object> getAllPaginations(String name, Pageable pageable);
}
