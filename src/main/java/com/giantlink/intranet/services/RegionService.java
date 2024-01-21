package com.giantlink.intranet.services;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;

import com.giantlink.intranet.exceptions.GlAlreadyExistException;
import com.giantlink.intranet.exceptions.GlNotFoundException;
import com.giantlink.intranet.models.requests.RegionRequest;
import com.giantlink.intranet.models.responses.RegionResponse;

public interface RegionService {

	RegionResponse add(RegionRequest regionRequest) throws GlAlreadyExistException;

	List<RegionResponse> getAll();

	RegionResponse get(Long id) throws GlNotFoundException;

	void delete(Long id) throws GlNotFoundException;

	RegionResponse update(Long id, RegionRequest regionRequest) throws GlNotFoundException;

	Map<String, Object> getAllPaginations(String name, Pageable pageable);

}
