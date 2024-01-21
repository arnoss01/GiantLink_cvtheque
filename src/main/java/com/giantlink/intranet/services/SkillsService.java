package com.giantlink.intranet.services;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;

import com.giantlink.intranet.exceptions.GlAlreadyExistException;
import com.giantlink.intranet.exceptions.GlNotFoundException;
import com.giantlink.intranet.models.requests.SkillsRequest;
import com.giantlink.intranet.models.responses.SkillsResponse;

public interface SkillsService {

	SkillsResponse add(SkillsRequest skillsRequest) throws GlAlreadyExistException;

	List<SkillsResponse> getAll();

	SkillsResponse get(Long id) throws GlNotFoundException;

	void delete(Long id) throws GlNotFoundException;

	SkillsResponse update(Long id, SkillsRequest skillsRequest) throws GlNotFoundException;

	Map<String, Object> getAllPaginations(String name, Pageable pageable);
}
