package com.giantlink.intranet.services.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.giantlink.intranet.entities.Candidacy;
import com.giantlink.intranet.entities.Qualification;
import com.giantlink.intranet.exceptions.GlAlreadyExistException;
import com.giantlink.intranet.exceptions.GlNotFoundException;
import com.giantlink.intranet.mappers.QualificationMapper;
import com.giantlink.intranet.models.requests.QualificationRequest;
import com.giantlink.intranet.models.responses.QualificationResponse;
import com.giantlink.intranet.repositories.CandidacyRepository;
import com.giantlink.intranet.repositories.QualificationRepository;
import com.giantlink.intranet.services.QualificationService;

@Service
public class QualificationServiceImpl implements QualificationService {

	@Autowired
	QualificationRepository qualificationRepository;

	@Autowired
	CandidacyRepository candidacyRepository;

	@Override
	public QualificationResponse add(QualificationRequest qualificationRequest)
			throws GlAlreadyExistException, GlNotFoundException {
		Optional<Qualification> qualificationfind = qualificationRepository.findByName(qualificationRequest.getName());
		if (qualificationfind.isPresent()) {
			throw new GlAlreadyExistException("qualification", Qualification.class.getSimpleName());
		}
		Optional<Candidacy> candidacyfind = candidacyRepository.findById(qualificationRequest.getCandidacyId());
		if (candidacyfind.isEmpty()) {
			throw new GlNotFoundException("candidacy", Candidacy.class.getSimpleName());
		}
		Qualification qualification = QualificationMapper.INSTANCE
				.QualificationRequestToQualification(qualificationRequest);
		qualification.setCandidacy(candidacyfind.get());
		return QualificationMapper.INSTANCE
				.qualificationToQualificationResponse(qualificationRepository.save(qualification));
	}

	@Override
	public List<QualificationResponse> getAll() {
		return QualificationMapper.INSTANCE.mapQualification(qualificationRepository.findAll());
	}

	@Override
	public QualificationResponse get(Long id) throws GlNotFoundException {
		Optional<Qualification> qualificationfind = qualificationRepository.findById(id);
		if (qualificationfind.isEmpty()) {
			throw new GlNotFoundException("qualification", Qualification.class.getSimpleName());
		}
		return QualificationMapper.INSTANCE.qualificationToQualificationResponse(qualificationfind.get());
	}

	@Override
	public void delete(Long id) throws GlNotFoundException {
		Optional<Qualification> qualificationfind = qualificationRepository.findById(id);
		if (qualificationfind.isEmpty()) {
			throw new GlNotFoundException("qualification", Qualification.class.getSimpleName());
		}
		qualificationRepository.delete(qualificationfind.get());
	}

	@Override
	public QualificationResponse update(Long id, QualificationRequest qualificationRequest) throws GlNotFoundException {
		System.out.println(qualificationRequest);
		Optional<Qualification> qualificationfind = qualificationRepository.findById(id);
		if (qualificationfind.isEmpty()) {
			throw new GlNotFoundException("qualification", Qualification.class.getSimpleName());
		}
		Optional<Candidacy> candidacyfind = candidacyRepository.findById(qualificationRequest.getCandidacyId());
		if (candidacyfind.isEmpty()) {
			throw new GlNotFoundException("candidacy", Candidacy.class.getSimpleName());
		}
		qualificationfind.get().setName(qualificationRequest.getName());
		qualificationfind.get().setWording(qualificationRequest.getWording());
		qualificationfind.get().setCandidacy(candidacyfind.get());
		return QualificationMapper.INSTANCE
				.qualificationToQualificationResponse(qualificationRepository.save(qualificationfind.get()));
	}

	@Override
	public Map<String, Object> getAllPaginations(String name, Pageable pageable) {
		List<QualificationResponse> qualificationResponses = new ArrayList<>();
		Page<Qualification> Qualification = (name.isBlank()) ? qualificationRepository.findAll(pageable)
				: qualificationRepository.findByNameContainingIgnoreCase(name, pageable);
		Qualification.getContent().forEach(qualification -> {
			qualificationResponses
					.add(QualificationMapper.INSTANCE.qualificationToQualificationResponse(qualification));
		});
		Map<String, Object> QualificationResponse = new HashMap<>();
		QualificationResponse.put("content", qualificationResponses);
		QualificationResponse.put("currentPage", Qualification.getNumber());
		QualificationResponse.put("totalElements", Qualification.getTotalElements());
		QualificationResponse.put("totalPages", Qualification.getTotalPages());
		return QualificationResponse;
	}

	@Override
	public List<QualificationResponse> responses() {
		// TODO Auto-generated method stub
		return QualificationMapper.INSTANCE.mapQualification(qualificationRepository.findAll());
	}

}
