package com.giantlink.intranet.services;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;

import com.giantlink.intranet.exceptions.GlAlreadyExistException;
import com.giantlink.intranet.exceptions.GlNotFoundException;
import com.giantlink.intranet.models.requests.CandidateRequest;
import com.giantlink.intranet.models.responses.CandidateResponse;

public interface CandidateService {

	CandidateResponse add(CandidateRequest candidatRequest) throws GlAlreadyExistException;

	List<CandidateResponse> getAll();

	CandidateResponse get(Long id) throws GlNotFoundException;

	void delete(Long id) throws GlNotFoundException;

	CandidateResponse update(Long id, CandidateRequest candidatRequest) throws GlNotFoundException;

	Map<String, Object> getAllPaginations(String email, Pageable pageable);

}
