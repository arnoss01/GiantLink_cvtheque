package com.giantlink.intranet.services;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;

import com.giantlink.intranet.exceptions.GlAlreadyExistException;
import com.giantlink.intranet.exceptions.GlNotFoundException;
import com.giantlink.intranet.models.requests.CvRequest;
import com.giantlink.intranet.models.requests.CvSearch;
import com.giantlink.intranet.models.responses.CvResponse;

public interface CvService {

	CvResponse add(CvRequest cvRequest) throws GlNotFoundException, GlAlreadyExistException;

	List<CvResponse> getAll();

	CvResponse get(Long id) throws GlNotFoundException;

	void delete(Long id) throws GlNotFoundException;

	Map<String, Object> getAllPaginations(String name, Pageable pageable);

	List<CvResponse> getByEntity(String Entity, String name);
	
	Map<String, Object> searchByEntities(CvSearch search, Pageable pageable);
}
