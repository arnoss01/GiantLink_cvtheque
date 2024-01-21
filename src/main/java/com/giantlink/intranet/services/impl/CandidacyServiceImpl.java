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

import com.giantlink.intranet.entities.Campaign;
import com.giantlink.intranet.entities.Candidacy;
import com.giantlink.intranet.entities.Cv;
import com.giantlink.intranet.entities.Qualification;
import com.giantlink.intranet.exceptions.GlAlreadyExistException;
import com.giantlink.intranet.exceptions.GlNotFoundException;
import com.giantlink.intranet.mappers.CandidacyMapper;
import com.giantlink.intranet.models.requests.CandidacyRequest;
import com.giantlink.intranet.models.responses.CandidacyResponse;
import com.giantlink.intranet.repositories.CampaignRepository;
import com.giantlink.intranet.repositories.CandidacyRepository;
import com.giantlink.intranet.repositories.CvRepository;
import com.giantlink.intranet.repositories.QualificationRepository;
import com.giantlink.intranet.services.CandidacyService;

@Service
public class CandidacyServiceImpl implements CandidacyService {

	@Autowired
	CandidacyRepository candidacyRepository;

	@Autowired
	CampaignRepository campaignRepository;

	@Autowired
	CvRepository cvRepository;

	@Autowired
	QualificationRepository qualificationRepository;

	@Transactional
	@Override
	public CandidacyResponse add(CandidacyRequest candidacyRequest)
			throws GlAlreadyExistException, GlNotFoundException {
		Optional<Campaign> campOptional = campaignRepository.findById(candidacyRequest.getIdCampaign());
		if (campOptional.isEmpty()) {
			throw new GlNotFoundException("campaign", Campaign.class.getSimpleName());
		}
		Optional<Cv> cvOptional = cvRepository.findById(candidacyRequest.getIdCv());
		if (cvOptional.isEmpty()) {
			throw new GlNotFoundException("cv", Cv.class.getSimpleName());
		}

		Candidacy candidacy = CandidacyMapper.INSTANCE.candidacyRequestToCandidacy(candidacyRequest);
		candidacy.setCampaign(campOptional.get());
		candidacy.setCv(cvOptional.get());

		return CandidacyMapper.INSTANCE.candidacyToCandidacyResponse(candidacyRepository.save(candidacy));
	}

	@Override
	public CandidacyResponse get(Long id) throws GlNotFoundException {
		Optional<Candidacy> candidacy = candidacyRepository.findById(id);
		if (candidacy.isEmpty()) {
			throw new GlNotFoundException("candidacy", Candidacy.class.getSimpleName());
		}
		return CandidacyMapper.INSTANCE.candidacyToCandidacyResponse(candidacyRepository.findById(id).get());
	}

	@Override
	public void delete(Long id) throws GlNotFoundException {
		Optional<Candidacy> candidacy = candidacyRepository.findById(id);
		if (candidacy.isEmpty()) {
			throw new GlNotFoundException("candidacy", Candidacy.class.getSimpleName());
		}
		candidacyRepository.deleteById(id);
	}

	@Override
	public CandidacyResponse update(Long id, CandidacyRequest candidacyRequest) throws GlNotFoundException {
		Optional<Candidacy> candidacy = candidacyRepository.findById(id);
		if (candidacy.isEmpty()) {
			throw new GlNotFoundException("candidacy", Candidacy.class.getSimpleName());
		}
		Optional<Campaign> campOptional = campaignRepository.findById(candidacyRequest.getIdCampaign());
		if (campOptional.isEmpty()) {
			throw new GlNotFoundException("campaign", Campaign.class.getSimpleName());
		}

		candidacy.get().setQualifications(new HashSet<>());
		candidacy.get().setCandidacyDate(candidacyRequest.getCandidacyDate());
		candidacy.get().setApplicationName(candidacyRequest.getApplicationName());
		candidacy.get().setCampaign(campOptional.get());

		for (Long idq : candidacyRequest.getIdQualifications()) {
			Optional<Qualification> qualification = qualificationRepository.findById(idq);
			if (qualification.isPresent()) {
				candidacy.get().getQualifications().add(qualification.get());
			}
		}

		return CandidacyMapper.INSTANCE.candidacyToCandidacyResponse(candidacyRepository.save(candidacy.get()));
	}

	@Override
	public Map<String, Object> getAllPaginations(String name, Pageable pageable) {
		List<CandidacyResponse> candidacyResponses = new ArrayList<>();
		Page<Candidacy> candidacys = (name.isBlank()) ? candidacyRepository.findAll(pageable)
				: candidacyRepository.findByApplicationNameContainingIgnoreCase(name, pageable);
		for (Candidacy candidacy : candidacys) {
			candidacyResponses.add(CandidacyMapper.INSTANCE.candidacyToCandidacyResponse(candidacy));
		}

		Map<String, Object> candidacyResponse = new HashMap<>();
		candidacyResponse.put("content", candidacyResponses);
		candidacyResponse.put("currentPage", candidacys.getNumber());
		candidacyResponse.put("totalElements", candidacys.getTotalElements());
		candidacyResponse.put("totalPages", candidacys.getTotalPages());
		return candidacyResponse;
	}

	@Override
	public List<CandidacyResponse> responses() {

		return CandidacyMapper.INSTANCE.mapResponses(candidacyRepository.findAll());
	}

}
