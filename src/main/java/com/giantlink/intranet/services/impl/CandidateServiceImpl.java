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

import com.giantlink.intranet.entities.Candidate;
import com.giantlink.intranet.exceptions.GlAlreadyExistException;
import com.giantlink.intranet.exceptions.GlNotFoundException;
import com.giantlink.intranet.mappers.CandidateMapper;
import com.giantlink.intranet.models.requests.CandidateRequest;
import com.giantlink.intranet.models.responses.CandidateResponse;
import com.giantlink.intranet.repositories.CandidateRepository;
import com.giantlink.intranet.services.CandidateService;

@Service
public class CandidateServiceImpl implements CandidateService {

	@Autowired
	CandidateRepository candidateRepository;

	@Transactional
	@Override
	public CandidateResponse add(CandidateRequest candidateRequest) throws GlAlreadyExistException {
		Optional<Candidate> findCandidate = candidateRepository.findByEmail(candidateRequest.getEmail());
		if (findCandidate.isPresent()) {
			throw new GlAlreadyExistException(" existe déjà", Candidate.class.getSimpleName());
		}
		Candidate candidate = CandidateMapper.INSTANCE.candidateRequestToCandidate(candidateRequest);
		return CandidateMapper.INSTANCE.candidateTocandidateResponse(candidateRepository.save(candidate));
	}

	@Override
	public List<CandidateResponse> getAll() {
		return CandidateMapper.INSTANCE.mapCandidate(candidateRepository.findAll());
	}

	@Override
	public CandidateResponse get(Long id) throws GlNotFoundException {
		Optional<Candidate> candidate = candidateRepository.findById(id);
		if (candidate.isEmpty()) {
			throw new GlNotFoundException("already exist", Candidate.class.getSimpleName());
		}
		return CandidateMapper.INSTANCE.candidateTocandidateResponse(candidate.get());
	}

	@Override
	public void delete(Long id) throws GlNotFoundException {
		Optional<Candidate> candidate = candidateRepository.findById(id);
		if (candidate.isEmpty()) {
			throw new GlNotFoundException("already exist", Candidate.class.getSimpleName());
		}
		candidateRepository.deleteById(id);
	}

	@Override
	public CandidateResponse update(Long id, CandidateRequest candidatRequest) throws GlNotFoundException {
		Optional<Candidate> candidate = candidateRepository.findById(id);
		if (candidate.isEmpty()) {
			throw new GlNotFoundException("already exist", Candidate.class.getSimpleName());
		}
		candidate.get().setFirstName(candidatRequest.getFirstName());
		candidate.get().setLastName(candidatRequest.getLastName());
		candidate.get().setCivility(candidatRequest.getCivility());
		candidate.get().setAdress(candidatRequest.getAdress());
		candidate.get().setBirthDate(candidatRequest.getBirthDate());
		candidate.get().setCity(candidatRequest.getCity());
		candidate.get().setAvailability(candidatRequest.getAvailability());
		candidate.get().setCountry(candidatRequest.getCountry());
		candidate.get().setEmail(candidatRequest.getEmail());
		candidate.get().setPhone(candidatRequest.getPhone());
		candidate.get().setMessage(candidatRequest.getMessage());
		candidateRepository.save(candidate.get());
		return CandidateMapper.INSTANCE.candidateTocandidateResponse(candidate.get());
	}

	@Override
	public Map<String, Object> getAllPaginations(String email, Pageable pageable) {

		List<CandidateResponse> candidateResponses = new ArrayList<>();
		Page<Candidate> candidates = (email.isBlank()) ? candidateRepository.findAll(pageable)
				: candidateRepository.findByEmailContainingIgnoreCase(email, pageable);
		for (Candidate Candidate : candidates) {
			candidateResponses.add(CandidateMapper.INSTANCE.candidateTocandidateResponse(Candidate));
		}

		Map<String, Object> CandidateResponse = new HashMap<>();
		CandidateResponse.put("content", candidateResponses);
		CandidateResponse.put("currentPage", candidates.getNumber());
		CandidateResponse.put("totalElements", candidates.getTotalElements());
		CandidateResponse.put("totalPages", candidates.getTotalPages());
		return CandidateResponse;

	}
}
