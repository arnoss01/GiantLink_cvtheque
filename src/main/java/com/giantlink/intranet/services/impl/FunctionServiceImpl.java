package com.giantlink.intranet.services.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.giantlink.intranet.entities.Campaign;
import com.giantlink.intranet.entities.Function;
import com.giantlink.intranet.exceptions.GlAlreadyExistException;
import com.giantlink.intranet.exceptions.GlNotFoundException;
import com.giantlink.intranet.mappers.FunctionMapper;
import com.giantlink.intranet.models.requests.FunctionRequest;
import com.giantlink.intranet.models.responses.FunctionResponse;
import com.giantlink.intranet.repositories.CampaignRepository;
import com.giantlink.intranet.repositories.FunctionRepository;
import com.giantlink.intranet.services.FunctionService;

@Service
public class FunctionServiceImpl implements FunctionService {

	@Autowired
	FunctionRepository functionRepository;

	@Autowired
	CampaignRepository campaignRepository;

	@Override
	public FunctionResponse add(FunctionRequest functionRequest) throws GlAlreadyExistException, GlNotFoundException {
		Optional<Function> findFunction = functionRepository.findByName(functionRequest.getName());
		if (findFunction.isPresent()) {
			throw new GlAlreadyExistException("function", Function.class.getSimpleName());
		}
		Optional<Campaign> findCampaign = campaignRepository.findById(functionRequest.getCampaignId());
		if (findCampaign.isEmpty()) {
			throw new GlNotFoundException("campaign", Campaign.class.getSimpleName());
		}
		Function function = FunctionMapper.INSTANCE.functionRequestToFunction(functionRequest);
		function.setCampaign(findCampaign.get());
		return FunctionMapper.INSTANCE.functionToFunctionResponse(functionRepository.save(function));
	}

	@Override
	public List<FunctionResponse> getAll() {
		return FunctionMapper.INSTANCE.mapEducation(functionRepository.findAll());
	}

	@Override
	public FunctionResponse get(Long id) throws GlNotFoundException {
		Optional<Function> function = functionRepository.findById(id);
		if (function.isEmpty()) {
			throw new GlNotFoundException("function", Function.class.getSimpleName());
		}
		return FunctionMapper.INSTANCE.functionToFunctionResponse(function.get());
	}

	@Override
	public void delete(Long id) throws GlNotFoundException {
		Optional<Function> function = functionRepository.findById(id);
		if (function.isEmpty()) {
			throw new GlNotFoundException("function", Function.class.getSimpleName());
		}
		functionRepository.deleteById(id);
	}

	@Override
	public FunctionResponse update(Long id, FunctionRequest functionRequest) throws GlNotFoundException {

		Optional<Function> function = functionRepository.findById(id);
		if (function.isEmpty()) {
			throw new GlNotFoundException("function", Function.class.getSimpleName());
		}
		Optional<Campaign> findCampaign = campaignRepository.findById(functionRequest.getCampaignId());
		if (findCampaign.isEmpty()) {
			throw new GlNotFoundException("campaign", Campaign.class.getSimpleName());
		}
		function.get().setDescription(functionRequest.getDescription());
		function.get().setName(functionRequest.getName());
		function.get().setCampaign(findCampaign.get());

		functionRepository.save(function.get());

		return FunctionMapper.INSTANCE.functionToFunctionResponse(function.get());
	}

	@Override
	public Map<String, Object> getAllPaginations(String name, Pageable pageable) {
		List<FunctionResponse> functionResponses = new ArrayList<>();
		Page<Function> functions = (name.isBlank()) ? functionRepository.findAll(pageable)
				: functionRepository.findByNameContainingIgnoreCase(name, pageable);
		functions.getContent().forEach(function -> {
			FunctionResponse response = FunctionResponse.builder().id(function.getId())
					.description(function.getDescription()).name(function.getName()).build();

			functionResponses.add(response);
		});
		Map<String, Object> functionResponse = new HashMap<>();
		functionResponse.put("content", functionResponses);
		functionResponse.put("currentPage", functions.getNumber());
		functionResponse.put("totalElements", functions.getTotalElements());
		functionResponse.put("totalPages", functions.getTotalPages());
		return functionResponse;
	}

}
