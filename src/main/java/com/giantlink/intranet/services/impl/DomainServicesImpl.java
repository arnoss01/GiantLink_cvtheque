package com.giantlink.intranet.services.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.giantlink.intranet.entities.Cv;
import com.giantlink.intranet.entities.Domain;
import com.giantlink.intranet.exceptions.GlAlreadyExistException;
import com.giantlink.intranet.exceptions.GlNotFoundException;
import com.giantlink.intranet.mappers.DomainMapper;
import com.giantlink.intranet.models.requests.DomainRequest;
import com.giantlink.intranet.models.responses.DomainResponse;
import com.giantlink.intranet.repositories.CvRepository;
import com.giantlink.intranet.repositories.DomainRepository;
import com.giantlink.intranet.services.DomainService;

@Service
public class DomainServicesImpl implements DomainService {
	@Autowired
	DomainRepository domainRepository;

	@Autowired
	CvRepository cvRepository;

	@Transactional
	@Override
	public DomainResponse add(DomainRequest domainRequest) throws GlAlreadyExistException {

		Optional<Domain> findDomain = domainRepository.findByName(domainRequest.getName());
		if (findDomain.isPresent()) {
			throw new GlAlreadyExistException("Domains", Domain.class.getSimpleName());
		}

		Domain newdomain = DomainMapper.INSTANCE.domainRequestTodomain(domainRequest);
		newdomain.setCvs(new HashSet<Cv>());

		return DomainMapper.INSTANCE.domainToDomainResponse(domainRepository.save(newdomain));
	}

	@Override
	public DomainResponse get(Long id) throws GlNotFoundException {

		Optional<Domain> domain = domainRepository.findById(id);
		if (domain.isEmpty()) {
			throw new GlNotFoundException("Domains", Domain.class.getSimpleName());
		}

		return DomainMapper.INSTANCE.domainToDomainResponse(domainRepository.findById(id).get());
	}

	@Override
	public List<DomainResponse> getAll() {

		List<DomainResponse> alldom = new ArrayList<DomainResponse>();

		domainRepository.findAll().forEach(av -> alldom.add(DomainMapper.INSTANCE.domainToDomainResponse(av)));

		return alldom;
	}

	@Override
	public void delete(Long id) throws GlNotFoundException {

		Optional<Domain> domain = domainRepository.findById(id);
		if (domain.isEmpty()) {
			throw new GlNotFoundException("Domains", Domain.class.getSimpleName());
		}

		domainRepository.deleteById(id);

	}

	@Override
	public DomainResponse update(Long id, DomainRequest domainRequest) throws GlNotFoundException {

		Optional<Domain> findDomain = domainRepository.findById(id);
		if (findDomain.isEmpty()) {
			throw new GlNotFoundException("Domains", Domain.class.getSimpleName());
		}

		Domain domain = domainRepository.findById(id).get();

		domain.setDuration(domainRequest.getDuration());
		domain.setName(domainRequest.getName());

		domainRepository.save(domain);

		return DomainMapper.INSTANCE.domainToDomainResponse(domain);
	}

	@Override
	public Map<String, Object> getAllPaginations(String domain, Pageable pageable) {
		List<DomainResponse> domainResponses = new ArrayList<>();
		Page<Domain> domains = (domain.isBlank()) ? domainRepository.findAll(pageable)
				: domainRepository.findByNameContainingIgnoreCase(domain, pageable);
		domains.forEach(d -> {
			DomainResponse response = DomainResponse.builder().id(d.getId()).duration(d.getDuration())
					.name(d.getName()).build();
			domainResponses.add(response);
		});

		Map<String, Object> domainResponse = new HashMap<>();
		domainResponse.put("content", domainResponses);
		domainResponse.put("currentPage", domains.getNumber());
		domainResponse.put("totalElements", domains.getTotalElements());
		domainResponse.put("totalPages", domains.getTotalPages());

		return domainResponse;
	}
}
