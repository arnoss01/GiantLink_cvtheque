package com.giantlink.intranet.services.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.giantlink.intranet.entities.Region;
import com.giantlink.intranet.exceptions.GlAlreadyExistException;
import com.giantlink.intranet.exceptions.GlNotFoundException;
import com.giantlink.intranet.mappers.RegionMapper;
import com.giantlink.intranet.models.requests.RegionRequest;
import com.giantlink.intranet.models.responses.RegionResponse;
import com.giantlink.intranet.repositories.RegionRepository;
import com.giantlink.intranet.services.RegionService;

@Service
public class RegionServiceImpl implements RegionService {

	@Autowired
	RegionRepository regionRepository;

	@Transactional
	@Override
	public RegionResponse add(RegionRequest regionRequest) throws GlAlreadyExistException {

		Optional<Region> findRegion = regionRepository.findByName(regionRequest.getName());
		Region newRegion = null;
		if (findRegion.isPresent()) {
			throw new GlAlreadyExistException("Region", Region.class.getSimpleName());
		} else {
			newRegion = Region.builder().name(regionRequest.getName()).build();
			regionRepository.save(newRegion);
		}

		return RegionMapper.INSTANCE.regionToRegionResponse(newRegion);
	}

	@Override
	public List<RegionResponse> getAll() {
		List<RegionResponse> allres = new ArrayList<RegionResponse>();
		regionRepository.findAll().forEach(reg -> allres.add(RegionMapper.INSTANCE.regionToRegionResponse(reg)));
		return allres;
	}

	@Override
	public RegionResponse get(Long id) throws GlNotFoundException {
		Optional<Region> region = regionRepository.findById(id);
		if (region.isEmpty()) {
			throw new GlNotFoundException("Region", Region.class.getSimpleName());
		}
		return RegionMapper.INSTANCE.regionToRegionResponse(regionRepository.findById(id).get());
	}

	@Override
	public void delete(Long id) throws GlNotFoundException {
		Optional<Region> region = regionRepository.findById(id);
		if (region.isEmpty()) {
			throw new GlNotFoundException("Region", Region.class.getSimpleName());
		}
		regionRepository.deleteById(id);
	}

	@Override
	public RegionResponse update(Long id, RegionRequest regionRequest) throws GlNotFoundException {
		Optional<Region> regionfind = regionRepository.findById(id);
		if (regionfind.isEmpty()) {
			throw new GlNotFoundException("Region", Region.class.getSimpleName());
		}
		Region region = regionRepository.findById(id).get();
		region.setName(regionRequest.getName());
		regionRepository.save(region);
		return RegionMapper.INSTANCE.regionToRegionResponse(region);
	}

	@Override
	public Map<String, Object> getAllPaginations(String name, Pageable pageable) {
		List<RegionResponse> regionResponses = new ArrayList<>();
		Page<Region> regions = (name.isBlank()) ? regionRepository.findAll(pageable)
				: regionRepository.findByNameContainingIgnoreCase(name, pageable);
		regions.getContent().forEach(availability -> {
			RegionResponse response = RegionResponse.builder().id(availability.getId()).name(availability.getName())
					.build();

			regionResponses.add(response);
		});
		Map<String, Object> regionResponse = new HashMap<>();
		regionResponse.put("content", regionResponses);
		regionResponse.put("currentPage", regions.getNumber());
		regionResponse.put("totalElements", regions.getTotalElements());
		regionResponse.put("totalPages", regions.getTotalPages());
		return regionResponse;
	}

}
