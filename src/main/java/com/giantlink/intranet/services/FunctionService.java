package com.giantlink.intranet.services;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;

import com.giantlink.intranet.exceptions.GlAlreadyExistException;
import com.giantlink.intranet.exceptions.GlNotFoundException;
import com.giantlink.intranet.models.requests.FunctionRequest;
import com.giantlink.intranet.models.responses.FunctionResponse;

public interface FunctionService {

	FunctionResponse add(FunctionRequest function) throws GlAlreadyExistException, GlNotFoundException;

	List<FunctionResponse> getAll();

	FunctionResponse get(Long id) throws GlNotFoundException;

	void delete(Long id) throws GlNotFoundException;

	FunctionResponse update(Long id, FunctionRequest function) throws GlNotFoundException;

	Map<String, Object> getAllPaginations(String name, Pageable pageable);

}
