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

import com.giantlink.intranet.entities.Campaign;
import com.giantlink.intranet.entities.Contract;
import com.giantlink.intranet.exceptions.GlAlreadyExistException;
import com.giantlink.intranet.exceptions.GlNotFoundException;
import com.giantlink.intranet.mappers.ContractMapper;
import com.giantlink.intranet.models.requests.ContractRequest;
import com.giantlink.intranet.models.responses.ContractResponse;
import com.giantlink.intranet.repositories.CampaignRepository;
import com.giantlink.intranet.repositories.ContractRepository;
import com.giantlink.intranet.services.ContractService;

@Service
public class ContractServiceImpl implements ContractService {

	@Autowired
	ContractRepository contractRepository;

	@Autowired
	ContractMapper contractMapper;

	@Autowired
	CampaignRepository campaignRepository;

	@Transactional
	@Override
	public ContractResponse add(ContractRequest contractRequest) throws GlAlreadyExistException, GlNotFoundException {
		Optional<Contract> contratfind = contractRepository.findByName(contractRequest.getName());
		if (contratfind.isPresent()) {
			throw new GlAlreadyExistException("Contract", Contract.class.getSimpleName());
		}
		Optional<Campaign> campaignfind = campaignRepository.findByName(contractRequest.getName());
		if (campaignfind.isPresent()) {
			throw new GlAlreadyExistException("Campaign", Campaign.class.getSimpleName());
		}
		Campaign campaign = campaignRepository.findById(contractRequest.getCampaignId()).get();

		Contract newContract = Contract.builder().campaign(campaign).name(contractRequest.getName())
				.description(contractRequest.getDescription()).build();

		contractRepository.save(newContract);
		return contractMapper.contractToContractResponse(newContract);
	}

	@Override
	public List<ContractResponse> getAll() {
		List<ContractResponse> allres = new ArrayList<ContractResponse>();

		contractRepository.findAll().forEach(av -> allres.add(contractMapper.contractToContractResponse(av)));

		return allres;
	}

	@Override
	public ContractResponse get(Long id) throws GlNotFoundException {
		Optional<Contract> contract = contractRepository.findById(id);
		if (contract.isEmpty()) {
			throw new GlNotFoundException("Contract", Contract.class.getSimpleName());
		}
		return contractMapper.contractToContractResponse(contractRepository.findById(id).get());

	}

	@Override
	public void delete(Long id) throws GlNotFoundException {
		Optional<Contract> contract = contractRepository.findById(id);
		if (contract.isEmpty()) {
			throw new GlNotFoundException("Contract", Contract.class.getSimpleName());
		}
		contractRepository.deleteById(id);
	}

	@Override
	public ContractResponse update(Long id, ContractRequest contractRequest) throws GlNotFoundException {

		Optional<Contract> contract = contractRepository.findById(id);
		if (contract.isEmpty()) {
			throw new GlNotFoundException("Contract", Contract.class.getSimpleName());
		}
		Contract contract2 = contractRepository.findById(id).get();
		contract2.setName(contractRequest.getName());
		contract2.setDescription(contractRequest.getDescription());

		Optional<Campaign> campaignfind = campaignRepository.findById(contractRequest.getCampaignId());
		if (campaignfind.isEmpty()) {
			throw new GlNotFoundException("Campaign", Campaign.class.getSimpleName());
		}

		Campaign campaign = campaignRepository.findById(contractRequest.getCampaignId()).get();

		contract2.setCampaign(campaign);

		contractRepository.save(contract2);

		return contractMapper.contractToContractResponse(contract2);
	}

	@Override
	public Map<String, Object> getAllPaginations(String name, Pageable pageable) {
		List<ContractResponse> contractResponses = new ArrayList<>();
		Page<Contract> contracts = (name.isBlank()) ? contractRepository.findAll(pageable)
				: contractRepository.findByNameContainingIgnoreCase(name, pageable);
		contracts.getContent().forEach(contract -> {
			ContractResponse response = ContractResponse.builder().id(contract.getId())
					.description(contract.getDescription()).name(contract.getName()).build();

			contractResponses.add(response);
		});
		Map<String, Object> ContractResponse = new HashMap<>();
		ContractResponse.put("content", contractResponses);
		ContractResponse.put("currentPage", contracts.getNumber());
		ContractResponse.put("totalElements", contracts.getTotalElements());
		ContractResponse.put("totalPages", contracts.getTotalPages());
		return ContractResponse;
	}

}
