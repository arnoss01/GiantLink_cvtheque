package com.giantlink.intranet.services;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;

import com.giantlink.intranet.exceptions.GlAlreadyExistException;
import com.giantlink.intranet.exceptions.GlNotFoundException;
import com.giantlink.intranet.models.requests.CampaignRequest;
import com.giantlink.intranet.models.responses.CampaignResponse;

public interface CampaignService {

	CampaignResponse add(CampaignRequest campaign) throws GlAlreadyExistException, GlNotFoundException;

	List<CampaignResponse> getAll();

	CampaignResponse get(Long id) throws GlNotFoundException;

	void delete(Long id) throws GlNotFoundException;

	CampaignResponse update(Long id, CampaignRequest campaign) throws GlNotFoundException;

	Map<String, Object> getAllPaginations(String name, Pageable pageable);
}
