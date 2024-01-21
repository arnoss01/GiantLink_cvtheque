package com.giantlink.intranet.services;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;

import com.giantlink.intranet.exceptions.GlAlreadyExistException;
import com.giantlink.intranet.exceptions.GlNotFoundException;
import com.giantlink.intranet.models.requests.LanguageRequest;
import com.giantlink.intranet.models.responses.LanguageResponse;

public interface LanguageService {

	LanguageResponse add(LanguageRequest languageRequest) throws GlAlreadyExistException;

	List<LanguageResponse> getAll();

	LanguageResponse get(Long id) throws GlNotFoundException;

	void delete(Long id) throws GlNotFoundException;

	LanguageResponse update(Long id, LanguageRequest languageRequest) throws GlNotFoundException;

	Map<String, Object> getAllPaginations(String name, Pageable pageable);
}
