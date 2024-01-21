package com.giantlink.intranet.services;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;

import com.giantlink.intranet.exceptions.GlAlreadyExistException;
import com.giantlink.intranet.exceptions.GlNotFoundException;
import com.giantlink.intranet.models.requests.QualificationRequest;
import com.giantlink.intranet.models.responses.QualificationResponse;

public interface QualificationService {

	QualificationResponse add(QualificationRequest qualificationRequest)
			throws GlAlreadyExistException, GlNotFoundException;

	List<QualificationResponse> getAll();

	QualificationResponse get(Long id) throws GlNotFoundException;

	void delete(Long id) throws GlNotFoundException;

	QualificationResponse update(Long id, QualificationRequest qualificationRequest) throws GlNotFoundException;
	
	List<QualificationResponse> responses();

	Map<String, Object> getAllPaginations(String name, Pageable pageable);
}
