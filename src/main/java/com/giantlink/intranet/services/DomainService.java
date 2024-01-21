package com.giantlink.intranet.services;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;

import com.giantlink.intranet.exceptions.GlAlreadyExistException;
import com.giantlink.intranet.exceptions.GlNotFoundException;
import com.giantlink.intranet.models.requests.DomainRequest;
import com.giantlink.intranet.models.responses.DomainResponse;

public interface DomainService {

	DomainResponse add(DomainRequest domainRequest) throws GlAlreadyExistException;

	DomainResponse get(Long id) throws GlNotFoundException;

	List<DomainResponse> getAll();

	void delete(Long id) throws GlNotFoundException;

	DomainResponse update(Long id, DomainRequest domainRequest) throws GlNotFoundException;

	Map<String, Object> getAllPaginations(String domain, Pageable pageable);

}
