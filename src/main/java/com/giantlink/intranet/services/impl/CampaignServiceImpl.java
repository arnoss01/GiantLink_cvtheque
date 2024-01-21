package com.giantlink.intranet.services.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.giantlink.intranet.entities.Campaign;
import com.giantlink.intranet.entities.Region;
import com.giantlink.intranet.exceptions.GlAlreadyExistException;
import com.giantlink.intranet.exceptions.GlNotFoundException;
import com.giantlink.intranet.mappers.CampaignMapper;
import com.giantlink.intranet.models.requests.CampaignRequest;
import com.giantlink.intranet.models.responses.CampaignResponse;
import com.giantlink.intranet.repositories.CampaignRepository;
import com.giantlink.intranet.repositories.ContractRepository;
import com.giantlink.intranet.repositories.FunctionRepository;
import com.giantlink.intranet.repositories.RegionRepository;
import com.giantlink.intranet.services.CampaignService;

@Service
public class CampaignServiceImpl implements CampaignService {

	@Autowired
	CampaignRepository campaignRepository;

	@Autowired
	FunctionRepository functionRepository;

	@Autowired
	RegionRepository regionRepository;

	@Autowired
	ContractRepository contractRepository;

	@Override
	public CampaignResponse add(CampaignRequest campaignRequest) throws GlAlreadyExistException, GlNotFoundException {
		Optional<Campaign> campaignFind = campaignRepository.findByName(campaignRequest.getName());
		if (campaignFind.isPresent()) {
			throw new GlAlreadyExistException("campaign", Campaign.class.getSimpleName());
		}
		
		Campaign campaign = CampaignMapper.INSTANCE.campaignRequestToCampaign(campaignRequest);
		
		campaign.setRegions(new HashSet<Region>());

		if (campaignRequest.getRegionsIds() != null && !campaignRequest.getRegionsIds().isEmpty()) {

			campaignRequest.getRegionsIds().forEach(r -> {
				Optional<Region> optional = regionRepository.findById(r);

				campaign.getRegions().add(optional.get());
			});
		}


		return CampaignMapper.INSTANCE.campaignToCampaignResponse(campaignRepository.save(campaign));
	}

	@Override
	public List<CampaignResponse> getAll() {
		return CampaignMapper.INSTANCE.mapCampaign(campaignRepository.findAll());
	}

	@Override
	public CampaignResponse get(Long id) throws GlNotFoundException {
		Optional<Campaign> campaign = campaignRepository.findById(id);
		if (campaign.isEmpty()) {
			throw new GlNotFoundException("campaign", Campaign.class.getSimpleName());
		}
		return CampaignMapper.INSTANCE.campaignToCampaignResponse(campaign.get());
	}

	@Override
	public void delete(Long id) throws GlNotFoundException {
		Optional<Campaign> campaign = campaignRepository.findById(id);
		if (campaign.isEmpty()) {
			throw new GlNotFoundException("campaign", Campaign.class.getSimpleName());
		}
		campaignRepository.deleteById(id);
	}

	@Override
	public CampaignResponse update(Long id, CampaignRequest campaignRequest) throws GlNotFoundException {
		Campaign campaign = campaignRepository.findById(id).get();

		campaign.setClosingDate(campaignRequest.getClosingDate());
		campaign.setDescription(campaignRequest.getDescription());
		campaign.setName(campaignRequest.getName());
		campaign.setNbPositions(campaignRequest.getNbPositions());

		Set<Region> regions = new HashSet<>();
		for (Long idc : campaignRequest.getRegionsIds()) {
			Optional<Region> regionFind = regionRepository.findById(idc);
			if (regionFind.isEmpty()) {
				throw new GlNotFoundException("region", Region.class.getSimpleName());
			}
			regions.add(regionFind.get());
		}

		campaign.setRegions(regions);

		return CampaignMapper.INSTANCE.campaignToCampaignResponse(campaignRepository.save(campaign));
	}

	@Override
	public Map<String, Object> getAllPaginations(String name, Pageable pageable) {
		List<CampaignResponse> compaignResponses = new ArrayList<>();
		Page<Campaign> compaigns = (name.isBlank()) ? campaignRepository.findAll(pageable)
				: campaignRepository.findByNameContainingIgnoreCase(name, pageable);
		compaigns.getContent().forEach(campaign -> {
			compaignResponses.add(CampaignMapper.INSTANCE.campaignToCampaignResponse(campaign));
		});
		Map<String, Object> compaignResponse = new HashMap<>();
		compaignResponse.put("content", compaignResponses);
		compaignResponse.put("currentPage", compaigns.getNumber());
		compaignResponse.put("totalElements", compaigns.getTotalElements());
		compaignResponse.put("totalPages", compaigns.getTotalPages());
		return compaignResponse;
	}

}
