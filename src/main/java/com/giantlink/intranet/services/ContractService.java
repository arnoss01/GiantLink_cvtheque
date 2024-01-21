package com.giantlink.intranet.services;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;

import com.giantlink.intranet.exceptions.GlAlreadyExistException;
import com.giantlink.intranet.exceptions.GlNotFoundException;
import com.giantlink.intranet.models.requests.ContractRequest;
import com.giantlink.intranet.models.responses.ContractResponse;

public interface ContractService {
	ContractResponse add(ContractRequest function) throws GlAlreadyExistException, GlNotFoundException;

	List<ContractResponse> getAll();

	ContractResponse get(Long id) throws GlNotFoundException;

	void delete(Long id) throws GlNotFoundException;

	ContractResponse update(Long id, ContractRequest function) throws GlNotFoundException;

	Map<String, Object> getAllPaginations(String name, Pageable pageable);

}
